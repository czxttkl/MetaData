using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using EvoNetClus.dataStruct;

namespace EvoNetClus.network
{
    public class Network
    {
        public Entity[] entityList;
        public SparseTensor network; //objID->sparseMatrix(objID, type, attributeID, count)

        public Network(int n)
        {
            entityList = new Entity[n];
            for (int i = 0; i < n; i++)
            {
                entityList[i] = new Entity();
            }
            network = new SparseTensor();
        }

        public void loadDict(string dir, string dictFile, int typeNum)
        {
            for (int i = 0; i < typeNum; i++)
            {
                string curDictFile = dir + i + dictFile;
                entityList[i].loadData(curDictFile, true);
            }
        }

        public void loadNetwork(string dir, string networkFile)
        {
            network.loadData(dir + networkFile);

            //random shuffle here to avoid cluster collaction
            //network.randomize(2 * network.tensor.Count);
        }

        public static Network operator +(Network net1, Network net2)
        {
            int n = net1.entityList.Length;
            Network curNet = new Network(n);
            Entity[] curEntList = new Entity[n];

            //aggregate the entitylist
            for (int i = 0; i < n; i++)
            {
                curEntList[i] = new Entity();
                curEntList[i].copy(net1.entityList[i]);
                curEntList[i].additems(net2.entityList[i]);
            }

            //aggregate the network
            SparseTensor curNetTensor = new SparseTensor();
            curNetTensor = net1.network + net2.network;

            curNet.entityList = curEntList;
            curNet.network = curNetTensor;

            return curNet;
        }
    }
}
