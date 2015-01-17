using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.IO;

using EvoNetClus.dataStruct;
using EvoNetClus.network;

namespace EvoNetClus.clustering
{
    //define a clustering
    public class Cluster
    {
        public static int MAXClusID = 0; //start from 1

        public SparseMatrix objectClusMat;
        public SparseMatrix clusObjectMat;

        public SparseMatrix[] attriClusMatList; //array stands for types, [attriID, clusID] = count
        public SparseMatrix[] clusAttriMatList;

        public HashSet<int> objectList;
        public HashSet<int> clusterList;

        public SparseVector clusterSizeVec;
        public SparseMatrix clusterPriorMat; //store the prior clusters and its size for current new cluster

        public SparseMatrix clusTypeCountMat;

        public int typeNum;

        public Cluster(int n)
        {
            objectList = new HashSet<int>();
            clusterList = new HashSet<int>();

            objectClusMat = new SparseMatrix();
            clusObjectMat = new SparseMatrix();

            attriClusMatList = new SparseMatrix[n];
            clusAttriMatList = new SparseMatrix[n];

            for (int i = 0; i < n; i++)
            {
                attriClusMatList[i] = new SparseMatrix();
                clusAttriMatList[i] = new SparseMatrix();
            }

            clusterSizeVec = new SparseVector();
            clusterPriorMat = new SparseMatrix();

            clusTypeCountMat = new SparseMatrix();

            typeNum = n;
        }

        public void assignCluster(int objID, SparseMatrix typeIDMat, int oldClusID, int newClusID)
        {
            if (newClusID == oldClusID)
                return;
            //take care of general information for the clusters
            if (!objectList.Contains(objID))
            {
                objectList.Add(objID);
            }

            if (!clusterList.Contains(newClusID))
            {
                clusterList.Add(newClusID);
            }

            //take care of target objects in the clusters
            if (oldClusID != -1) //no old cluster labels
            {
                objectClusMat[objID, oldClusID] = 0;
                clusObjectMat[oldClusID, objID] = 0;
            }

            objectClusMat[objID, newClusID] = 1;
            clusObjectMat[newClusID, objID] = 1;

            //take care of attribute objects in the clusters
            foreach (int type in typeIDMat.matrix.Keys)
            {
                SparseVector IDCountVec = typeIDMat.getRow(type);
                
                foreach (KeyValuePair<int, double> kvp in IDCountVec.vector)
                {
                    //remove from old clusters
                    if (oldClusID != -1)
                    {
                        attriClusMatList[type][kvp.Key, oldClusID] = attriClusMatList[type][kvp.Key, oldClusID] - kvp.Value;
                        clusAttriMatList[type][oldClusID, kvp.Key] = clusAttriMatList[type][oldClusID, kvp.Key] - kvp.Value;
                        clusTypeCountMat[oldClusID,type] -= kvp.Value; 
                    }

                    //add to new clusters
                    attriClusMatList[type][kvp.Key, newClusID] = attriClusMatList[type][kvp.Key, newClusID] + kvp.Value;
                    clusAttriMatList[type][newClusID, kvp.Key] = clusAttriMatList[type][newClusID, kvp.Key] + kvp.Value;
                    clusTypeCountMat[newClusID, type] += kvp.Value;
                }
            }

            //take care of cluster sizes
            if (oldClusID != -1)
            {
                clusterSizeVec[oldClusID]--;
                //deal with the situation of removing a cluster
                if (clusterSizeVec[oldClusID] == 0)
                {
                    clusterList.Remove(oldClusID);
                    clusObjectMat.removeRow(oldClusID);
                }
            }

            clusterSizeVec[newClusID]++;
        }

        public static Cluster operator*(Cluster clus, double weight)
        {
            int n = clus.attriClusMatList.Length;
            Cluster newClus = new Cluster(n);
            newClus.objectList = clus.objectList;
            newClus.clusterList = clus.clusterList;
            newClus.objectClusMat = clus.objectClusMat;
            newClus.clusObjectMat = clus.clusObjectMat;
            
            //need to discount attribute objects counts
            for (int i = 0; i < n; i++)
            {
                newClus.attriClusMatList[i] = clus.attriClusMatList[i] * weight;
                newClus.clusAttriMatList[i] = clus.clusAttriMatList[i] * weight;
            }

            newClus.clusTypeCountMat = clus.clusTypeCountMat * weight;

            //discount size as well
            foreach(KeyValuePair<int,double> kvp in clus.clusterSizeVec.vector)
            {
                int clusID = kvp.Key;
                double value = kvp.Value;
                newClus.clusterSizeVec.vector.Add(clusID, weight*value);
            }

            return newClus;
        }

        public static Cluster operator+(Cluster clus1, Cluster clus2)
        {
            int n = clus1.attriClusMatList.Length;
            Cluster newClus = new Cluster(n);
            newClus.objectList.UnionWith(clus1.objectList);
            newClus.objectList.UnionWith(clus2.objectList);

            newClus.clusterList.UnionWith(clus1.clusterList);
            newClus.clusterList.UnionWith(clus2.clusterList);

            newClus.objectClusMat = clus1.objectClusMat + clus2.objectClusMat;
            newClus.clusObjectMat = clus1.clusObjectMat + clus2.objectClusMat;

            for (int i = 0; i < n; i++)
            {
                newClus.attriClusMatList[i] = clus1.attriClusMatList[i] + clus2.attriClusMatList[i];
                newClus.clusAttriMatList[i] = clus1.clusAttriMatList[i] + clus2.clusAttriMatList[i];
            }

            newClus.clusTypeCountMat = clus1.clusTypeCountMat + clus2.clusTypeCountMat;
            newClus.clusterSizeVec = clus1.clusterSizeVec + clus2.clusterSizeVec;

            return newClus;
        }

        public void output(string dir, int topK, Entity [] entList)
        {
            if (!Directory.Exists(dir))
            {
                DirectoryInfo dirInfo = Directory.CreateDirectory(dir);
            }
            FileStream fs_obj = File.Create(dir + "obj_clus.txt");
            FileStream fs_pz = File.Create(dir + "PZ.txt");
            FileStream fs_topk = File.Create(dir + "topk.txt");

            StreamWriter sw_obj = new StreamWriter(fs_obj);
            StreamWriter sw_pz = new StreamWriter(fs_pz);
            StreamWriter sw_topk = new StreamWriter(fs_topk);

            foreach (int objID in objectList)
            {
                SparseVector vec = objectClusMat.getRow(objID);
                foreach (KeyValuePair<int, double> kvp in vec.vector)
                {
                    sw_obj.WriteLine(objID + "\t" + kvp.Key);
                }
            }

            sw_pz.WriteLine("Cluster Number = {0}", clusterList.Count);
            foreach (int clusID in clusterSizeVec.vector.Keys)
            {
                sw_pz.WriteLine(clusID + "\t" + clusterSizeVec[clusID]);
                SparseVector priorVec = clusterPriorMat.getRow(clusID);
                sw_pz.Write("Priors: ");
                if (priorVec != null)
                {
                    foreach (int priorClus in priorVec.vector.Keys)
                    {
                        sw_pz.Write("\t" + priorClus + ":" + priorVec[priorClus]);
                    }
                }
                else
                {
                }
                sw_pz.WriteLine();
            }

            foreach (int clusID in clusterSizeVec.vector.Keys)
            //foreach (int clusID in clusterList)
            {
                sw_topk.WriteLine("Cluster ID = {0}, size = {1}", clusID, clusterSizeVec[clusID]);
                for (int type = 0; type < typeNum; type++)
                {
                    sw_topk.WriteLine("Cluster ID = {0}, Type = {1}", clusID, type);
                    SparseVector curTypeIDVec = clusAttriMatList[type].getRow(clusID);
                    if (curTypeIDVec != null)
                    {
                        List<KeyValuePair<int, double>> kvpList = curTypeIDVec.sort();
                        for (int i = 0; i < topK && i< kvpList.Count; i++)
                        {
                            int ID= kvpList[i].Key;
                            double count = kvpList[i].Value;
                            // We remove outputs of Id to make results more clear.
                            //sw_topk.WriteLine(ID + "\t" + entList[type][ID] + "\t" + count);
                            sw_topk.WriteLine(entList[type][ID] + "\t" + count);
                        }
                    }
                }
            }

            sw_obj.Close();
            sw_pz.Close();
            sw_topk.Close();
        }
    }
}
