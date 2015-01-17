using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EvoNetClus.clustering
{
    public class TimeCluster
    {
        public Dictionary<int, Cluster> timeClusterList;//timewin -> Cluster

        public TimeCluster()
        {
            timeClusterList = new Dictionary<int, Cluster>();
        }

        public void addCurTimeCluster(int time, Cluster clusRes)
        {
            timeClusterList.Add(time, clusRes);
        }

        public Cluster this[int tidx]
        {
            get
            {
                if (timeClusterList.ContainsKey(tidx))
                {
                    return timeClusterList[tidx];
                }
                else
                {
                    return null;
                }
            }
            set
            {
                timeClusterList[tidx] = value;
            }
        }
    }
}
