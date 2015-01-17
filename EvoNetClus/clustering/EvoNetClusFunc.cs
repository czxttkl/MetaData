using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using EvoNetClus.dataStruct;
using EvoNetClus.network;

namespace EvoNetClus.clustering
{
    public class EvoNetClusFunc
    {
        /*input*/
        //parameters
        public double alpha; //control new cluster generating rate
        public Dictionary<int, double> betaVec; //control background knowledge, default 1 as uniform distribution

        //network with time
        public TimeNetwork networkTS;
        public int typeNum;

        /*output*/
        public TimeCluster clusterTS; // to reduce memory usage, write to file promptly

        string indir;
        string outdir;


        public EvoNetClusFunc(string networkDir, string outputDir, string networkFile, string dictFile, int typeNum, int startT, int endT, double alpha)
        {
            networkTS = new TimeNetwork(typeNum);
            clusterTS = new TimeCluster();
            this.typeNum = typeNum;

            //load network
            for (int t = startT; t <= endT; t++)
            {
                networkTS.loadData(t, networkDir + t + @"\", dictFile, typeNum, networkFile);
            }

            this.alpha = alpha;
            
            betaVec = new Dictionary<int, double>();
            // authors
            betaVec[0] = 0.4;
            // keywords
            betaVec[1] = 0.8;
            // author_cited
            betaVec[2] = 0.6;
            // venue
            betaVec[3] = 0.1;

            // original code
            //for (int i = 0; i < typeNum; i++)
            //{
                //control beta
            //    betaVec[i] = 0.2;
                //betaVec[i] = 0.5;
            // }

            indir = networkDir;
            outdir = outputDir;
        }

        /*main algorithm*/
        public void mainFunc(int startT, int endT, int winLen)
        {
            //double sigma = 0.8; //use 80% energy to control the windows in the history to use
            double lambda = 1; //strength of historical distributions 
            //int hisWinNum = (int)Math.Ceiling(-Math.Log(1 - sigma) / lambda); //history windows needs to consider
            int hisWinNum = 1;
            double gamma = alpha;

            /*loop*/
            int totalWin = (int)Math.Ceiling((double)(endT - startT + 1)/(double)winLen);
            for (int curWin = 0; curWin < totalWin; curWin++)
            {
                /* 1. prepare curNet */
                int firstT = curWin * winLen + startT;
                int lastT = (curWin+1)*winLen + startT - 1;

                Network curWinNet = new Network (typeNum);

                for (int curT = firstT; curT <= lastT; curT++)
                {
                    curWinNet = curWinNet + networkTS[curT];
                }

                /* 2. prepare historical clustering results, aggregated discounted countings for each type of objects*/
                Cluster histClus = new Cluster(typeNum);
                for (int tw = curWin - 1; tw >= curWin - hisWinNum && tw>=0; tw--)
                {
                    //double weight = Math.Exp(-lambda * (curWin - tw));
                    double clusSize = clusterTS[tw].clusterSizeVec.getSum();//total objectsize
                    double weight = lambda / clusSize;
                    histClus = histClus + clusterTS[tw] * weight;
                    //histClus = histClus + clusterTS[tw];
                }

                /* 3. clustering: call clustering function */
                Cluster curClusRes = GreedyClustering.run(histClus, curWinNet, alpha, gamma,lambda, betaVec, typeNum);

                /* 4. store current clustering results*/
                clusterTS[curWin] = curClusRes;
                //curClusRes.output(outdir + curWin + @"\", 100, networkTS.globalDict.entityList);
                curClusRes.output(outdir + curWin + @"\", 20, networkTS.globalDict.entityList);

                //remove unused output;
                int lastUsedWin = curWin - hisWinNum;
                if (lastUsedWin >= 0)
                {
                    clusterTS.timeClusterList.Remove(lastUsedWin);
                }
            }
        }
    }
}
