using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using EvoNetClus.clustering;

namespace EvoNetClus
{
    class Program
    {
        static void Main(string[] args)
        {
            //string indir = @"..\..\..\..\Data\DBLPNetTensor\";
            //string indir = @"..\..\..\..\Data\DeliciousTSR\";
            
            string indir = @"..\..\..\networks_input\";
            string outdir = @"..\..\..\networks_output4\";

            // She used 1 as alpha which leads to too many clusters.
            //EvoNetClusFunc evoFunc = new EvoNetClusFunc(indir, outdir, "network.tsr", ".dict", 3, 2000, 2013, 1/*alpha*/);
            EvoNetClusFunc evoFunc = new EvoNetClusFunc(indir, outdir, "network.tsr", ".dict", 3, 2000, 2013, 0.5/*alpha*/);
            evoFunc.mainFunc(2000, 2013, 1/*winlen*/);

            //EvoNetClusFunc evoFunc = new EvoNetClusFunc(indir, outdir, "delicious", ".dict", 3, 1, 4, 5);
            //evoFunc.mainFunc(1, 4, 1);
        }
    }
}
