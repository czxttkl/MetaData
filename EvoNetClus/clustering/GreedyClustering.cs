using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using EvoNetClus.network;
using EvoNetClus.dataStruct;
namespace EvoNetClus.clustering
{
    class GreedyClustering
    {
        //gamma is second level parameter
        public static Cluster run(Cluster histClus, Network curNet, double alpha, double gamma, double lambda, Dictionary<int, double> betaVec, int typeNum)
        {
            Cluster curClus = new Cluster(typeNum); //clustering for each type

            double log_alpha = Math.Log(alpha);

            SparseVector logPriorPropVec = new SparseVector();
            if (histClus.clusterList.Count != 0 )
            {
                foreach (KeyValuePair<int, double> kvp in histClus.clusterSizeVec.vector)
                {
                    logPriorPropVec[kvp.Key] = Math.Log(kvp.Value); //log (lambda* pi_k)
                }
            }
            
            /*****************************/
            //background 2: uniform background
            /*********************************************/
            Dictionary<int, HashSet<int>> attributeDict = new Dictionary<int, HashSet<int>>();
            for (int type = 0; type < typeNum; type++)
            {
                attributeDict[type] = new HashSet<int>();
                attributeDict[type].UnionWith(histClus.attriClusMatList[type].getKeys());
                attributeDict[type].UnionWith(curNet.entityList[type].IDHT.Keys);
            }

            /*********************************************/
            //foreach object in curNet, adjust their cluster membership, until converge
            double epsi = double.MaxValue;
            double EPSI = 0.0001;
            int INITITER = 0;

            HashSet<int> candidateClus = new HashSet<int>();

            //initial clusters are historical clusters. It should be commented if do not need prior clusters
            if (histClus.clusterList.Count != 0)
            {
                candidateClus.UnionWith(histClus.clusterList);
            }

            int iter = 0;
            int objSize = curNet.network.tensor.Count;

            /******* control types that will be used **************/
            HashSet<int> subTypeSet = new HashSet<int> ();
            subTypeSet.Add(0); //C
            subTypeSet.Add(1); //A
            subTypeSet.Add(2); //T


            /********* calculate the p(oi) under H, and under each possible priors beforehand*/
            //SparseMatrix obj_priorTable_Mat = new SparseMatrix();
            Dictionary<int, int> obj_priorDict = new Dictionary<int,int> ();

            if (histClus.clusterList.Count != 0)
            {
                foreach (int objID in curNet.network.tensor.Keys)
                {
                    SparseVector clusProbVec = new SparseVector();
                    double log_prob_in_newclus = 0;

                    SparseMatrix typeIDMat = curNet.network.tensor[objID];

                    //prior clusters
                    foreach (int clusID in histClus.clusterList)
                    {
                        //double clusSize = /*histClus.clusterSizeVec[clusID] + */curClus.clusterSizeVec[clusID];
                        double clusSize = histClus.clusterSizeVec[clusID];

                        double log_prob_in_clus = 0;

                        for (int type = 0; type < typeNum; type++)
                        {
                            if (!subTypeSet.Contains(type))
                                continue;

                            SparseVector IDCountVec = typeIDMat.getRow(type);
                            if (IDCountVec == null)
                                continue;

                            double typeProb = 1;
                            double curObjTotalCount = IDCountVec.getSum();

                            double histClus_total_count = histClus.clusTypeCountMat[clusID, type];
                            //double curClus_total_Count = curClus.clusTypeCountMat[clusID, type];
                            double curClus_total_Count = 0;

                            int i = 1;// record total current count

                            foreach (KeyValuePair<int, double> kvp in IDCountVec.vector)
                            {
                                int ID = kvp.Key;
                                double count = kvp.Value;

                                //if (!attributeDict[type].ContainsKey(ID))
                                //{
                                //    Console.WriteLine("Type {0} ID {1}is not in dictionary", type, ID);
                                //}

                                double hist_item_count = histClus.clusAttriMatList[type][clusID, ID];
                                //double cur_item_count = curClus.clusAttriMatList[type][clusID, ID];
                                double cur_item_count = 0;

                                for (int itemCount = 1; itemCount <= count; itemCount++)
                                {

                                    //background 1:
                                    //typeProb *= /*Math.Log*/((attributeDict[type][ID] * betaVec[type] + hist_item_count + cur_item_count + count - itemCount)
                                    //    / (typeCount[type] * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i));
                                    //logTypeProb -= Math.Log(attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i);

                                    //background 2:
                                    typeProb *= /*Math.Log*/((1 * betaVec[type] + hist_item_count + cur_item_count + count - itemCount)
                                        / (attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i));

                                    i++;
                                }
                            }

                            log_prob_in_clus += Math.Log(typeProb);
                        }

                        clusProbVec[clusID] = log_prob_in_clus;
                    }

                    //unseen clusters
                    for (int type = 0; type < typeNum; type++)
                    {
                        if (!subTypeSet.Contains(type))
                            continue;
                        SparseVector IDCountVec = typeIDMat.getRow(type);
                        if (IDCountVec == null)
                            continue;
                        double typeProb = 1;
                        double curObjTotalCount = IDCountVec.getSum();
                        int i = 1;
                        foreach (KeyValuePair<int, double> kvp in IDCountVec.vector)
                        {
                            int ID = kvp.Key;
                            double count = kvp.Value;

                            for (int itemCount = 1; itemCount <= count; itemCount++)
                            {
                                //background 1:
                                //typeProb *= /*Math.Log*/((attributeDict[type][ID] * betaVec[type] + count - itemCount) / (typeCount[type] * betaVec[type] + curObjTotalCount - i));
                                //logTypeProb -= Math.Log(attributeDict[type].Count * betaVec[type] + curObjTotalCount - i);

                                //background 2:
                                typeProb *= /*Math.Log*/((1 * betaVec[type] + count - itemCount) / (attributeDict[type].Count * betaVec[type] + curObjTotalCount - i));

                                i++;
                            }
                        }
                        log_prob_in_newclus += Math.Log(typeProb);
                    }

                    clusProbVec[-1] = log_prob_in_newclus + log_alpha;

                    int curPrior = clusProbVec.getMax_Pos().Key;
                    obj_priorDict.Add(objID, curPrior);
                }
            }

            ////handle middle results of tables
            //int MAXTableID = 0; //start from 1
            //Dictionary<int, Cluster> groupTableDict = new Dictionary<int,Cluster> ();//record tables for each group
            //Dictionary<int, int> tableClusterDict = new Dictionary<int, int>();// record cluster for each table;
            //foreach (int priorID in obj_priorDict)
            //{
            //    Cluster TableList = new Cluster();
            //    groupTableDict.Add(priorID, TableList);
            //}

            SparseMatrix groupClusSizeMat = new SparseMatrix();
            foreach (int priorID in obj_priorDict.Keys)
            {
                SparseVector vec = new SparseVector();
                groupClusSizeMat.setRow(priorID, vec);
            }

            SparseVector betaCoeffVec = new SparseVector(); //first level component coefficient, non-normalized
            betaCoeffVec[-1] = alpha;
            //betaCoeffVec[-1] = 1; //alpha logn (every one has alpha)
            while (epsi > EPSI && iter < 1000)
            {
                int handledObjNum = 0;
                int clusChangedNum = 0;
                DateTime curTime = DateTime.Now;
                //randomize the network
                curNet.network.randomize(objSize);

                //if (histClus.clusterList.Count != 0)
                {
                    //foreach (int objID in curNet.network.tensor.Keys)
                    //{
                    //    int priorID = obj_priorDict[objID];
                    //    SparseMatrix typeIDMat = curNet.network.tensor[objID];
                    //    //1. assign tables
                    //    int tID = getTableID(typeIDMat, histClus, curClus, groupTableDict[priorGroup].clusterSizeVec, gamma);

                    //    //2. assign clusters to tables
                    //    if (tID == -1) // a new table
                    //    {
                    //        int kID = getClusterID_new(typeIDMat,candidateClus, histClus, curClus, curClus.clusterSizeVec, alpha);
                    //        // change the clusters for all the objects in the table if needed
                    //        //...
                    //    }
                    //    else // a existing table
                    //    {
                    //        SparseMatrix tableTypeIDMat = new SparseMatrix();
                    //        int kID = getClusterID(tableTypeIDMat, candidateClus, histClus, curClus, curClus.clusterSizeVec, alpha);
                    //        //change the clusters for all the objects in the table if needed
                    //        //...
                    //    }

                        
                    //}

                    foreach (int objID in curNet.network.tensor.Keys)
                    {
                        //calculate the probability it belongs to each cluster
                        SparseVector clusProbVec = new SparseVector();
                        int newClusID = Cluster.MAXClusID;

                        SparseMatrix typeIDMat = curNet.network.tensor[objID];
                        int priorGroup = -1;
                        if(obj_priorDict.ContainsKey(objID))
                            priorGroup = obj_priorDict[objID];
                        //existing clusters
                        foreach (int clusID in candidateClus)
                        {
                            //double clusSize = /*histClus.clusterSizeVec[clusID] + */curClus.clusterSizeVec[clusID];
                            //double clusSize = histClus.clusterSizeVec[clusID] + curClus.clusterSizeVec[clusID];
                            double clusSize = histClus.clusterSizeVec[clusID] + groupClusSizeMat[priorGroup, clusID] + gamma * betaCoeffVec[clusID]/betaCoeffVec.getSum();//groupClusSize could be zero, that's why we need HDP, or priors for the size

                            double log_prob_in_clus = 0;

                            for (int type = 0; type < typeNum; type++)
                            {
                                if (!subTypeSet.Contains(type))
                                    continue;

                                SparseVector IDCountVec = typeIDMat.getRow(type);
                                if (IDCountVec == null)
                                    continue;

                                double typeProb = 1;
                                double curObjTotalCount = IDCountVec.getSum();

                                double histClus_total_count = histClus.clusTypeCountMat[clusID, type];
                                double curClus_total_Count = curClus.clusTypeCountMat[clusID, type];

                                int i = 1;// record total current count

                                foreach (KeyValuePair<int, double> kvp in IDCountVec.vector)
                                {
                                    int ID = kvp.Key;
                                    double count = kvp.Value;

                                    //if (!attributeDict[type].ContainsKey(ID))
                                    //{
                                    //    Console.WriteLine("Type {0} ID {1}is not in dictionary", type, ID);
                                    //}

                                    double hist_item_count = histClus.clusAttriMatList[type][clusID, ID];
                                    double cur_item_count = curClus.clusAttriMatList[type][clusID, ID];

                                    for (int itemCount = 1; itemCount <= count; itemCount++)
                                    {
                                        if (curClus.objectClusMat[objID, clusID] == 1)//clusID contains objID, needs to remove it
                                        {
                                            //background 1:
                                            //typeProb *= /*Math.Log*/((attributeDict[type][ID] * betaVec[type] + hist_item_count + cur_item_count /*- count + count*/ - itemCount)
                                            //    / (typeCount[type] * betaVec[type] + histClus_total_count + curClus_total_Count /*- curObjTotalCount + curObjTotalCount*/ - i));
                                            //logTypeProb -= Math.Log(attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count - curClus_total_Count + curObjTotalCount - i);

                                            //background 2:
                                            typeProb *= /*Math.Log*/((1 * betaVec[type] + hist_item_count + cur_item_count /*- count + count*/ - itemCount)
                                                / (attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count  /*+ curObjTotalCount - curObjTotalCount*/ - i));
                                        }
                                        else
                                        {
                                            //background 1:
                                            //typeProb *= /*Math.Log*/((attributeDict[type][ID] * betaVec[type] + hist_item_count + cur_item_count + count - itemCount)
                                            //    / (typeCount[type] * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i));
                                            //logTypeProb -= Math.Log(attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i);

                                            //background 2:
                                            typeProb *= /*Math.Log*/((1 * betaVec[type] + hist_item_count + cur_item_count + count - itemCount)
                                                / (attributeDict[type].Count * betaVec[type] + histClus_total_count + curClus_total_Count + curObjTotalCount - i));
                                        }
                                        i++;
                                    }
                                }

                                log_prob_in_clus += Math.Log(typeProb);
                            }
                            if (iter < INITITER)
                            {
                                clusProbVec[clusID] = log_prob_in_clus;
                            }
                            else
                            {
                                double tempClusSize = clusSize;
                                if (curClus.objectClusMat[objID, clusID] == 1) //clusID
                                {
                                    tempClusSize = tempClusSize - 1;
                                }
                                //if (tempClusSize == 0)
                                //{
                                //    clusProbVec[clusID] = log_prob_in_clus + log_alpha;
                                //}
                                //else
                                {
                                    clusProbVec[clusID] = log_prob_in_clus + Math.Log(tempClusSize);
                                }
                            }
                        }
                        //new cluster, only calculate if current objID is not the only object in its cluster and with no history
                        int oldClusID = -1;
                        bool needsNewCluster = true;
                        SparseVector assignVec = curClus.objectClusMat.getRow(objID);
                        if (assignVec != null)
                        {
                            if (assignVec.vector.Count != 0)
                                oldClusID = assignVec.getMax_Pos().Key;

                            if (oldClusID != -1 && curClus.clusterSizeVec[oldClusID] == 1)
                            {
                                needsNewCluster = false;
                            }

                            if (histClus.clusterList.Contains(oldClusID))
                            {
                                needsNewCluster = true;
                            }
                        }

                        if (needsNewCluster)
                        {
                            double log_prob_in_newclus = 0;

                            for (int type = 0; type < typeNum; type++)
                            {
                                if (!subTypeSet.Contains(type))
                                    continue;
                                SparseVector IDCountVec = typeIDMat.getRow(type);
                                if (IDCountVec == null)
                                    continue;
                                double typeProb = 1;
                                double curObjTotalCount = IDCountVec.getSum();
                                int i = 1;
                                foreach (KeyValuePair<int, double> kvp in IDCountVec.vector)
                                {
                                    int ID = kvp.Key;
                                    double count = kvp.Value;

                                    for (int itemCount = 1; itemCount <= count; itemCount++)
                                    {
                                        //background 2:
                                        typeProb *= /*Math.Log*/((1 * betaVec[type] + count - itemCount) / (attributeDict[type].Count * betaVec[type] + curObjTotalCount - i));

                                        i++;
                                    }

                                }
                                log_prob_in_newclus += Math.Log(typeProb);
                            }
                            if (iter < INITITER)
                            {
                                clusProbVec[newClusID] = log_prob_in_newclus;
                            }
                            else
                            {
                                clusProbVec[newClusID] = log_prob_in_newclus + Math.Log( gamma * (betaCoeffVec[-1]/betaCoeffVec.getSum()));
                            }

                        }

                        //pick the cluster with the largest logprob
                        KeyValuePair<int, double> maxClusLogP = clusProbVec.getMax_Pos();

                        if (oldClusID != maxClusLogP.Key)
                        {
                            clusChangedNum++;

                            //code for test
                            if (epsi < 0.0001)
                                Console.WriteLine("\tObj {0} from {1} to {2}", objID, oldClusID, maxClusLogP.Key);
                        }

                        curClus.assignCluster(objID, typeIDMat, oldClusID, maxClusLogP.Key);

                        handledObjNum++;

                        //update candidateClus
                        if (oldClusID != -1 && !curClus.clusterList.Contains(oldClusID))
                        {
                            //candidateClus.Remove(oldClusID);
                            // This sentence was originally here.
                            // groupClusSizeMat[priorGroup, oldClusID]--;
                            //betaCoeffVec[oldClusID] = 0;
                        }
                        if (maxClusLogP.Key == newClusID)//assign to a new cluster
                        {
                            candidateClus.Add(newClusID);
                            Console.WriteLine("maxClus = {0}, objects handled = {1}", newClusID, handledObjNum);
                            Cluster.MAXClusID++;
                        }

                        if (maxClusLogP.Key != oldClusID)
                        {   
                            // We move the sentence here.
                            groupClusSizeMat[priorGroup, oldClusID]--;
                            groupClusSizeMat[priorGroup, maxClusLogP.Key]++;
                            betaCoeffVec[maxClusLogP.Key] = Math.Log(curClus.clusterSizeVec[maxClusLogP.Key]+1);
                        }
                    }
                }
               
                TimeSpan timeSpan = DateTime.Now - curTime;
                epsi = (double)clusChangedNum / objSize;
                Console.WriteLine("Iteration {0}: cluster number = {1}, epsi = {2}, execution time = {3}", ++iter, curClus.clusterList.Count, epsi, timeSpan);
            }
            curClus.clusterPriorMat = groupClusSizeMat.getTranspose();        
            return curClus;
        }
    }
}
