using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.IO;

namespace EvoNetClus.dataStruct
{
    public class SparseVector
    {
        public SortedDictionary<int, double> vector;

        public SparseVector()
        {
            vector = new SortedDictionary<int, double>();
        }

        public bool ContainsKey(int ID)
        {
            return (vector.ContainsKey(ID));
        }

        public SortedDictionary<int, double>.KeyCollection getKeys()
        {
            return vector.Keys;
        }

        public HashSet<int> getNeighbors()
        {
            HashSet<int> neiborSet = new HashSet<int>(vector.Keys);
            return neiborSet;
        }

        public void clear()
        {
            vector.Clear();
        }

        public void copy(SparseVector sv)
        {
            this.vector.Clear();
            foreach (KeyValuePair<int, double> kvp in sv.vector)
            {
                vector.Add(kvp.Key, kvp.Value);
            }
        }

        public void remove(int ID)
        {
            if (vector.ContainsKey(ID))
                vector.Remove(ID);
        }

        public double this[int ID]
        {
            get
            {
                if (vector.ContainsKey(ID))
                    return vector[ID];
                else
                    return 0; // default 0 when sparse
            }
            set
            {
                if (value != 0)// sparse, only store non-zero values
                {
                    if (!vector.ContainsKey(ID))
                    {
                        vector.Add(ID, value);
                    }
                    else
                    {
                        vector[ID] = value;
                    }
                }
                else
                {
                    vector.Remove(ID);
                }
            }
        }

        public int getCount()
        {
            return vector.Count;
        }

        public void normalized()
        {
            if (vector.Count == 0)
                return;
            double sum = getSum();
            SparseVector newVec = this / sum;
            vector = newVec.vector;
        }

        public double getSum()
        {
            double sum = 0;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                sum += kvp.Value;
            }

            return sum;
        }

        public double norm1()
        {
            double sum = 0;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                sum += Math.Abs(kvp.Value);
            }

            return sum;
        }

        public double getMax()
        {
            double max = double.MinValue;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double curVal = kvp.Value;
                if (curVal > max)
                {
                    max = curVal;
                }
            }
            return max;
        }

        public double getMin()
        {
            double min = double.MaxValue;
  
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double curVal = kvp.Value;
                if (curVal < min)
                {
                    min = curVal;
                }
            }
            return min;
        }

        public KeyValuePair<int, double> getMin_Pos()
        {
            double min = double.MaxValue;
            int minPos = -1;
          
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double curVal = kvp.Value;
                if (curVal < min)
                {
                    min = curVal;
                    minPos = kvp.Key;
                }
            }
            KeyValuePair<int, double> posValuePair = new KeyValuePair<int, double>(minPos, min);

            return posValuePair;
        }

        public KeyValuePair<int, double> getMax_Pos()
        {
            double max = Double.MinValue;
            int maxPos = -1;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double curVal = kvp.Value;
                if (curVal > max)
                {
                    max = curVal;
                    maxPos = kvp.Key;
                }
            }

            KeyValuePair<int, double> posValuePair = new KeyValuePair<int, double>(maxPos, max);

            return posValuePair;
        }

        public double getIDivergenceTo(SparseVector sv)
        {
            double dist = 0;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double sourceV = sv[kvp.Key]; // should not equal to zero
                dist += kvp.Value * (Math.Log(kvp.Value) - Math.Log(sourceV + Double.Epsilon)) - kvp.Value + sourceV;
            }
            return dist;
        }

        public double getKLDivergenceTo(SparseVector sv)
        {
            double dist = 0;
            double sum1 = this.getSum();
            double sum2 = sv.getSum();
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double sourceV = sv[kvp.Key]; // should not equal to zero
                dist += (kvp.Value / sum1) * (Math.Log(kvp.Value / sum1) - Math.Log((sourceV / (sum2 + Double.Epsilon) + Double.Epsilon)));
            }
            return dist;
        }

        public double getSquareSum()
        {
            double sum = 0;
            foreach (KeyValuePair<int, double> kvp in vector)
            {
                double value = kvp.Value;
                sum += value * value;

            }
            return sum;
        }

        public double norm2()
        {

            return Math.Sqrt(getSquareSum());
        }


        public SparseVector getSubVec(HashSet<int> IDSet)
        {
            SparseVector subVec = new SparseVector();

            if (vector.Count < IDSet.Count)
            {
                foreach (KeyValuePair<int, double> kvp in vector)
                {
                    int ID = kvp.Key;
                    double value = kvp.Value;
                    if (IDSet.Contains(ID))
                    {
                        subVec[ID] = value;
                    }
                }
            }
            else
            {
                foreach (int ID in IDSet)
                {
                    if (vector.ContainsKey(ID))
                    {
                        subVec[ID] = vector[ID];
                    }
                }
            }
            return subVec;
        }

        // min
        public static SparseVector getMinVec(SparseVector v1, SparseVector v2)
        {
            SparseVector minVec = new SparseVector();
            SortedDictionary<int, double>.KeyCollection keys1 = v1.getKeys();
            SortedDictionary<int, double>.KeyCollection keys2 = v2.getKeys();

            foreach (int ID in keys1)
            {
                if (v2.ContainsKey(ID))
                {
                    minVec[ID] = Math.Min(v1[ID], v2[ID]);
                }
            }

            return minVec;
        }

        //max
        public static SparseVector getMaxVec(SparseVector v1, SparseVector v2)
        {
            SparseVector maxVec = new SparseVector();
            SortedDictionary<int, double>.KeyCollection keys1 = v1.getKeys();
            SortedDictionary<int, double>.KeyCollection keys2 = v2.getKeys();

            foreach (int ID in keys1)
            {
                if (v2.ContainsKey(ID))
                {
                    maxVec[ID] = Math.Max(v1[ID], v2[ID]);
                }
                else
                    maxVec[ID] = v1[ID];
            }

            foreach (int ID in keys2)
            {
                if (!v1.ContainsKey(ID))
                    maxVec[ID] = v2[ID];
            }

            return maxVec;
        }


        //Operators
        public static SparseVector operator +(SparseVector v1, SparseVector v2)
        {
            SparseVector sum = new SparseVector();
            //SortedDictionary<int, double>.KeyCollection keys1 = v1.getKeys();
            //SortedDictionary<int, double>.KeyCollection keys2 = v2.getKeys();

            if (v1 != null)
            {
                if (v2 != null)
                {
                    foreach (int ID in v1.vector.Keys)
                    {
                        if (v2.ContainsKey(ID))
                        {
                            sum[ID] = v1[ID] + v2[ID];
                        }
                        else
                            sum[ID] = v1[ID];
                    }
                }
                else
                {
                    sum.copy(v1);
                }
            }
            if (v2 != null)
            {
                if (v1 != null)
                {
                    foreach (int ID in v2.vector.Keys)
                    {
                        if (!v1.ContainsKey(ID))
                            sum[ID] = v2[ID];
                    }
                }
                else
                {
                    sum.copy(v2);
                }
            }

            return sum;
        }

        public static SparseVector operator -(SparseVector v1, SparseVector v2)
        {
            SparseVector diff = new SparseVector();
            SortedDictionary<int, double>.KeyCollection keys1 = v1.getKeys();
            SortedDictionary<int, double>.KeyCollection keys2 = v2.getKeys();

            foreach (int ID in keys1)
            {
                if (v2.ContainsKey(ID))
                {
                    diff[ID] = v1[ID] - v2[ID];
                }
                else
                    diff[ID] = v1[ID];
            }

            foreach (int ID in keys2)
            {
                if (!v1.ContainsKey(ID))
                    diff[ID] = -v2[ID];
            }

            return diff;
        }


        public static SparseVector operator *(SparseVector v, double scaleFactor)
        {
            SparseVector newVec = new SparseVector();
            if (scaleFactor == 0)
            {
                return newVec;
            }

            foreach (KeyValuePair<int, double> kvp in v.vector)
            {
                newVec[kvp.Key] = kvp.Value * scaleFactor;
            }

            return newVec;

        }

        public static SparseVector operator /(SparseVector v, double scaleFactor)
        {
            if (scaleFactor == 0)
                return null;
            SparseVector newVec = new SparseVector();
            foreach (KeyValuePair<int, double> kvp in v.vector)
            {
                newVec[kvp.Key] = kvp.Value / scaleFactor;
            }

            return newVec;

        }

        public static SparseVector operator *(SparseVector sv1, SparseMatrix sm2)
        {
            SparseVector curVec = new SparseVector();

            SortedDictionary<int, double>.KeyCollection rowkeys = sv1.getKeys();
            foreach (int midID in rowkeys)
            {
                SparseVector rowht2 = sm2.getRow(midID);
                double firstV = sv1[midID];
                if (rowht2 == null)
                    continue;
                SortedDictionary<int, double>.KeyCollection row2keys = rowht2.getKeys();
                foreach (int colID in row2keys)
                {
                    curVec[colID] = curVec[colID] + firstV * rowht2[colID];
                }
            }

            return curVec;
        }

        public static double dotprod(SparseVector v1, SparseVector v2)
        {
            double res = 0;
            //compare the count of v1 and v2, use the smaller one as base
            if (v1.getCount() < v2.getCount())
            {
                foreach (KeyValuePair<int, double> kvp in v1.vector)
                {
                    res += kvp.Value * v2[kvp.Key];
                }
            }
            else
            {
                foreach (KeyValuePair<int, double> kvp in v2.vector)
                {
                    res += v1[kvp.Key] * kvp.Value;
                }
            }

            return res;
        }

        //H(v1,v2); 0log0 = 0; xlog0 = -inf
        public static double crossEnt(SparseVector v1, SparseVector v2)
        {
            double res = 0;
            foreach (KeyValuePair<int, double> kvp in v2.vector)
            {
                if (v1.ContainsKey(kvp.Key))
                {
                    res -= v1[kvp.Key] * Math.Log(kvp.Value);
                }
            }

            //xlog0
            foreach (KeyValuePair<int, double> kvp in v1.vector)
            {
                if (!v2.ContainsKey(kvp.Key))
                {
                    res -= kvp.Value * Math.Log(double.Epsilon);
                }
            }

            return res;
        }

        public List<KeyValuePair<int, double>> sort()
        {
            List<KeyValuePair<int, double>> sortList = new List<KeyValuePair<int, double>>(vector);
            sortList.Sort
                (
                delegate(KeyValuePair<int, double> x, KeyValuePair<int, double> y)
                {
                    return y.Value.CompareTo(x.Value);
                }
                );
            return sortList;

        }
        //IO
        public void read(string file)
        {
            FileStream fs = File.OpenRead(file);
            StreamReader sr = new StreamReader(fs);

            string line = "";
            while ((line = sr.ReadLine()) != null)
            {
                string[] strAL = line.Split("\t".ToCharArray());
                int ID = int.Parse(strAL[0]);
                double value = double.Parse(strAL[1]);
                this[ID] = value;
            }

            sr.Close();
        }

        public void store(string file)
        {
            FileStream fs = File.Create(file);
            StreamWriter sw = new StreamWriter(fs);

            foreach (KeyValuePair<int, double> kvp in vector)
            {
                sw.WriteLine(kvp.Key + "\t" + kvp.Value);
            }

            sw.Close();
        }

    }
}
