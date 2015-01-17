using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EvoNetClus.network
{
    public class TimeNetwork
    {
        public GlobalDict globalDict;
        public Dictionary<int, Network> timeNetworkList;

        public TimeNetwork(int n)
        {
            globalDict = new GlobalDict(n);
            timeNetworkList = new Dictionary<int, Network>();
        }

        //time by time
        public void loadData(int time, string dir, string dictFile, int typeNum, string networkFile)
        {
            Network curNetwork = new Network(typeNum);
            curNetwork.loadDict(dir,dictFile, typeNum);
            curNetwork.loadNetwork(dir, networkFile);

            timeNetworkList.Add(time, curNetwork);

            //maintain global dictionary
            for (int i = 0; i < typeNum; i++)
            {
                globalDict.addDict(i, curNetwork.entityList[i]);
            }
        }

        public Network this[int tidx]
        {
            get
            {
                if (timeNetworkList.ContainsKey(tidx))
                {
                    return timeNetworkList[tidx];
                }
                else
                    return null;
            }
            set
            {
                timeNetworkList[tidx] = value;
            }
        }
    }
}
