using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Collections; 

namespace EvoNetClus.dataStruct
{
    public class SparseTensor
    {
        public Dictionary<int, SparseMatrix> tensor;

        public SparseTensor()
        {
            tensor = new Dictionary<int, SparseMatrix>();
        }

        public void copy(SparseTensor ts)
        {
            foreach (int id in ts.tensor.Keys)
            {
                SparseMatrix mat = new SparseMatrix();
                mat.copy(ts.tensor[id]);
                this.tensor.Add(id, mat);
            }
        }

        //t: shuffle times
        public void randomize(int t)
        {
            List<int> IDList = new List<int>();
            foreach (int id in tensor.Keys)
            {
                IDList.Add(id);
            }

            //shuffle 
            Random random1 = new Random(1);
            System.Threading.Thread.Sleep(1);
            Random random2 = new Random(2);

            for (int i = 0; i < t; i++)
            {
                //randomly pick one ID
                
                int curIdx = random1.Next(0, IDList.Count);
                //move to the last one
                int ID = IDList[curIdx];
                IDList.RemoveAt(curIdx);
                IDList.Add(ID);

                curIdx = random2.Next(0, IDList.Count);
                ID = IDList[curIdx];
                IDList.RemoveAt(curIdx);
                IDList.Insert(0, ID);
            }

            //reassign the tensor
            Dictionary<int, SparseMatrix> newTensor = new Dictionary<int,SparseMatrix> ();
            for (int i = 0; i < IDList.Count; i++)
            {
                int ID = IDList[i];
                newTensor.Add(ID, tensor[ID]);
            }
            tensor = newTensor;
        }

        public double this[int i, int j, int k]
        {
            get
            {
                if (tensor.ContainsKey(i))
                {
                    return tensor[i][j, k];
                }
                else
                {
                    return 0;
                }
            }
            set
            {
                if (tensor.ContainsKey(i))
                {
                    tensor[i][j, k] = value;
                }
                else
                {
                    SparseMatrix mat = new SparseMatrix();
                    mat[j, k] = value;
                    tensor[i] = mat;
                }
            }
        }

        public SparseVector getVector(int i, int j)
        {
            if (tensor.ContainsKey(i))
            {
                return tensor[i].getRow(j);
            }
            else
            {
                return null;
            }
        }

        public void setVector(int i, int j, SparseVector vec)
        {
            if (tensor.ContainsKey(i))
            {
                tensor[i].setRow(j, vec);
            }
            else
            {
                SparseMatrix mat = new SparseMatrix();
                mat.setRow(j, vec);
                tensor.Add(i, mat);
            }
        }

        public SparseMatrix getMatrix(int i)
        {
            if (tensor.ContainsKey(i))
            {
                return tensor[i];
            }
            else
            {
                return null;
            }
        }

        public void setMatrix(int i, SparseMatrix matrix)
        {
            tensor[i] = matrix;
        }

        public void GetVecNomalized()
        {
            foreach (int i in tensor.Keys)
            {
                tensor[i].rowNormalized();
            }
        }

        public double norm1()
        {
            double sum = 0;
            foreach (int id in tensor.Keys)
            {
                sum += tensor[id].norm1();
            }
            return sum;
        }

        public static SparseTensor operator +(SparseTensor st1, SparseTensor st2)
        {
            SparseTensor newST = new SparseTensor();
            foreach (KeyValuePair<int, SparseMatrix> kvp in st1.tensor)
            {
                if (st2.tensor.ContainsKey(kvp.Key))
                {
                    newST.tensor.Add(kvp.Key, kvp.Value + st2.getMatrix(kvp.Key));
                }
                else
                {
                    newST.tensor.Add(kvp.Key, kvp.Value);
                }
            }
            foreach (KeyValuePair<int, SparseMatrix> kvp in st2.tensor)
            {
                if (!st1.tensor.ContainsKey(kvp.Key))
                {
                    newST.tensor.Add(kvp.Key, kvp.Value);
                }
            }
            return newST;
        }

        public static SparseTensor operator -(SparseTensor st1, SparseTensor st2)
        {
            SparseTensor newST = new SparseTensor();
            foreach (KeyValuePair<int, SparseMatrix> kvp in st1.tensor)
            {
                if (st2.tensor.ContainsKey(kvp.Key))
                {
                    newST.tensor.Add(kvp.Key, kvp.Value - st2.getMatrix(kvp.Key));
                }
                else
                {
                    newST.tensor.Add(kvp.Key, kvp.Value);
                }
            }
            foreach (KeyValuePair<int, SparseMatrix> kvp in st2.tensor)
            {
                if (!st1.tensor.ContainsKey(kvp.Key))
                {
                    SparseMatrix tempMat = new SparseMatrix();
                    tempMat = tempMat - kvp.Value;
                    newST.tensor.Add(kvp.Key, tempMat);
                }
            }
            return newST;
        }

        //read files: format: dim1, dim2, dim3, count
        public void loadData(string file)
        {
            FileStream fs = File.OpenRead(file);
            StreamReader sr = new StreamReader(fs);

            string line = "";
            while ((line = sr.ReadLine()) != null)
            {
                string[] strAL = line.Split("\t".ToCharArray());
                int ID1 = int.Parse(strAL[0]);
                int ID2 = int.Parse(strAL[1]);
                int ID3 = int.Parse(strAL[2]);

                double count = double.Parse(strAL[3]);
                this[ID1, ID2, ID3] = count;
            }

            sr.Close();
        }
    }
}
