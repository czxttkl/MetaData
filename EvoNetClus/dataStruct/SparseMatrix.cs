using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.IO;

namespace EvoNetClus.dataStruct
{
    public class SparseMatrix
    {
        //store two copies for performance
        public SortedDictionary<int, SparseVector> matrix;//row based hash table

        public SparseMatrix()
        {
            matrix = new SortedDictionary<int, SparseVector>();
        }

        public SortedDictionary<int, SparseVector>.KeyCollection getKeys()
        {
            return matrix.Keys;
        }
        //get sparse row
        public SparseVector getRow(int rowID)
        {
            if (matrix.ContainsKey(rowID))
                return matrix[rowID];
            else
                return null;
        }

        public int getRowNum()
        {
            return matrix.Count;
        }
        

        //col set of current row
        public HashSet<int> getNeighborSet(int rowID)
        {
            if (!matrix.ContainsKey(rowID))
                return null;
            HashSet<int> rowColSet = new HashSet<int>(matrix[rowID].getKeys());
            return rowColSet;
        }

        //judge whether colID is in the matrix
        public bool ContainsCol(int colID)
        {
            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                if (kvp.Value.ContainsKey(colID))
                    return true;
            }
            return false;
        }

        public void setRow(int rowID, SparseVector rowVec)
        {
            if (rowVec != null && rowVec.getCount() != 0)
            {
                matrix[rowID] = rowVec;
            }
        }

        public SparseMatrix getSubMatrix(HashSet<int> rowIDSet)
        {
            SparseMatrix submatrix = new SparseMatrix();
            foreach (int ID in rowIDSet)
            {
                SparseVector rowVec = getRow(ID);
                submatrix.setRow(ID, rowVec);

            }
            return submatrix;
        }

        public SparseMatrix getSubMatrix(HashSet<int> rowIDSet, HashSet<int> colIDSet)
        {
            SparseMatrix submatrix = new SparseMatrix();
            foreach (int ID in rowIDSet)
            {
                SparseVector rowVec = getRow(ID);
                SparseVector subRowVec = rowVec.getSubVec(colIDSet);
                if (subRowVec.getCount() > 0)
                {
                    submatrix.setRow(ID, rowVec);
                }
            }
            return submatrix;
        }

        public SparseMatrix rowNormalize()
        {
            SparseMatrix newMat = new SparseMatrix();
            foreach (int rowID in matrix.Keys)
            {
                SparseVector rowVec = matrix[rowID];
                if (rowVec != null && rowVec.getCount() != 0)
                {
                    double sum = rowVec.getSum();
                    SparseVector newVec = rowVec / sum;
                    newMat.setRow(rowID, newVec);
                }
            }
            return newMat;
        }

        public SparseVector getSumofRows()//correct
        {
            SparseVector newVec = new SparseVector();

            foreach (int rowID in matrix.Keys)
            {
                SparseVector rowVec = (SparseVector)matrix[rowID];
                if (rowVec != null && rowVec.getCount() != 0)
                {
                    newVec[rowID] = rowVec.getSum();
                }
            }
            return newVec;
        }

        public SparseVector getColMaxVec()
        {
            SparseVector newVec = new SparseVector();

            foreach (int rowID in matrix.Keys)
            {
                SparseVector rowVec = (SparseVector)matrix[rowID];
                if (rowVec != null && rowVec.getCount() != 0)
                {
                    newVec[rowID] = rowVec.getMax();
                }
            }
            return newVec;
        }

        public double this[int rowID, int colID]
        {
            get
            {
                SparseVector rowVec = getRow(rowID);
                if (rowVec == null)
                {
                    return 0;
                }
                return rowVec[colID];
            }
            set
            {
                SparseVector rowVec = getRow(rowID);
                if (rowVec == null)
                {
                    rowVec = new SparseVector();
                    rowVec[colID] = value;
                    matrix[rowID] = rowVec;
                }
                else
                {
                    matrix[rowID][colID] = value;
                }

            }
        }

        public void remove(int rowID, int colID)
        {
            SparseVector rowVec = getRow(rowID);
            if (rowVec != null)
            {
                rowVec.remove(colID);
                matrix[rowID] = rowVec;
            }
        }

        public void removeRow(int rowID)
        {
            matrix.Remove(rowID);
        }

        public void removeCol(int colID)
        {
            foreach (int rowID in matrix.Keys)
            {
                matrix[rowID].remove(colID);
            }
        }

        public double getSum()
        {
            double sum = 0;
            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                SparseVector rowVec = kvp.Value;
                if (rowVec != null)
                {
                    sum += rowVec.getSum();
                }
            }
            return sum;
        }

        public double norm1()
        {
            double sum = 0;
            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                SparseVector rowVec = kvp.Value;
                if (rowVec != null)
                {
                    sum += rowVec.norm1();
                }
            }
            return sum;
        }

        public int getNNZ()
        {
            int nnz = 0;
            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                nnz += kvp.Value.getCount();
            }

            return nnz;
        }

        public void rowNormalized()
        {
            foreach (int rowID in matrix.Keys)
            {
                matrix[rowID].normalized();
            }
        }

        //only works for square matrix
        public SparseVector getDiagonal()
        {
            SparseVector diagVec = new SparseVector();
            foreach (int ID in matrix.Keys)
            {
                double curVal = this[ID, ID];
                if (curVal != 0)
                {
                    diagVec[ID] = curVal;
                }
            }
            return diagVec;
        }

        //get transpose relation
        public SparseMatrix getTranspose()
        {
            SparseMatrix transposeMat = new SparseMatrix();
            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                SparseVector rowVec = kvp.Value;
                int rowID = kvp.Key;
                foreach (KeyValuePair<int, double> kvpVec in rowVec.vector)
                {
                    int colID = kvpVec.Key;
                    transposeMat[colID, rowID] = rowVec[colID];
                }
            }
            return transposeMat;
        }

        public void copy(SparseMatrix sm)
        {
            matrix.Clear();
            foreach (KeyValuePair<int, SparseVector> kvp in sm.matrix)
            {
                SparseVector vec = new SparseVector();
                vec.copy(kvp.Value);
                matrix.Add(kvp.Key, vec);
            }
        }

        //propagation calculation
        public static SparseMatrix operator *(SparseMatrix sm1, SparseMatrix sm2)
        {
            SparseMatrix curMat = new SparseMatrix();

            foreach (KeyValuePair<int, SparseVector> kvp in sm1.matrix)
            {
                SparseVector rowht1 = kvp.Value;
                int rowID = kvp.Key;
                foreach (KeyValuePair<int, double> colVal1 in rowht1.vector)
                {
                    int midID = colVal1.Key;
                    SparseVector rowht2 = sm2.getRow(midID);
                    if (rowht2 == null)
                        continue;
                    foreach (KeyValuePair<int, double> colVal2 in rowht2.vector)
                    {
                        int colID = colVal2.Key;
                        curMat[rowID, colID] = curMat[rowID, colID] + colVal1.Value * colVal2.Value;
                    }
                }
            }
            return curMat;
        }

        public static SparseMatrix operator *(SparseMatrix sm, double weight)
        {
            SparseMatrix curMat = new SparseMatrix();
            foreach (KeyValuePair<int, SparseVector> kvp in sm.matrix)
            {
                SparseVector newVec = kvp.Value * weight;
                curMat.setRow(kvp.Key, newVec);
            }
            return curMat;
        }

        public static SparseMatrix operator + (SparseMatrix sm1, SparseMatrix sm2)
        {
            SparseMatrix curMat = new SparseMatrix();
            foreach (KeyValuePair<int, SparseVector> kvp in sm1.matrix)
            {
                SparseVector rowht1 = kvp.Value;
                int rowID = kvp.Key;
                SparseVector rowSum = new SparseVector();
                if (sm2.matrix.ContainsKey(rowID))
                {
                    rowSum = rowht1 + sm2.getRow(rowID);
                }
                else
                {
                    foreach (KeyValuePair<int, double> kvprow in rowht1.vector)
                    {
                        rowSum.vector.Add(kvprow.Key, kvprow.Value);
                    }
                }
                curMat.setRow(rowID, rowSum);
            }

            foreach (int ID in sm2.matrix.Keys)
            {
                if (!sm1.matrix.ContainsKey(ID))
                {
                    SparseVector rowht2 = sm2.getRow(ID);
                    SparseVector rowSum = new SparseVector();
                    foreach (KeyValuePair<int, double> kvprow in rowht2.vector)
                    {
                        rowSum.vector.Add(kvprow.Key, kvprow.Value);
                    }
                    curMat.setRow(ID, rowSum);
                }

            }
            return curMat;

        }

        public static SparseMatrix operator -(SparseMatrix sm1, SparseMatrix sm2)
        {
            SparseMatrix curMat = new SparseMatrix();

            foreach (KeyValuePair<int, SparseVector> kvp in sm1.matrix)
            {
                SparseVector rowht1 = kvp.Value;
                int rowID = kvp.Key;
                SparseVector rowDiff = new SparseVector();
                if (sm2.matrix.ContainsKey(rowID))
                {
                    rowDiff = rowht1 - sm2.getRow(rowID);
                }
                else
                {
                    foreach (KeyValuePair<int, double> kvprow in rowht1.vector)
                    {
                        rowDiff.vector.Add(kvprow.Key, kvprow.Value);
                    }
                }
                curMat.setRow(rowID, rowDiff);
            }

            foreach (int ID in sm2.matrix.Keys)
            {
                if (!sm1.matrix.ContainsKey(ID))
                {
                    SparseVector rowht2 = sm2.getRow(ID);
                    SparseVector rowDiff = new SparseVector();
                    foreach (KeyValuePair<int, double> kvprow in rowht2.vector)
                    {
                        rowDiff.vector.Add(kvprow.Key, -kvprow.Value);
                    }
                    curMat.setRow(ID, rowDiff);
                }

            }
            return curMat;
        }

        //IO
        //read linkweightlist
        public void read_List(string file)
        {
            FileStream fs = File.OpenRead(file);
            StreamReader sr = new StreamReader(fs);

            string line = "";

            while ((line = sr.ReadLine()) != null)
            {
                string[] strAL = line.Split("\t".ToCharArray());
                int fromID = int.Parse(strAL[0]);
                int toID = int.Parse(strAL[1]);
                double weight = double.Parse(strAL[2]);

                this[fromID, toID] += weight;
            }

            sr.Close();
        }

        //read
        public void read(string file)
        {
            FileStream fs_IDrow = File.OpenRead(file + ".IDrow");
            FileStream fs_colval = File.OpenRead(file + ".colval");

            StreamReader sr_IDrow = new StreamReader(fs_IDrow);
            BinaryReader br_colval = new BinaryReader(fs_colval);

            string line = "";
            int rowID = 0;
            int rowIdxFrom = 0;
            int rowIdxto = 0;

            while ((line = sr_IDrow.ReadLine()) != null)
            {
                string[] strAL = line.Split("\t".ToCharArray());
                rowID = Convert.ToInt32(strAL[0]);
                rowIdxto = Convert.ToInt32(strAL[1]);
                int length = rowIdxto - rowIdxFrom;

                SparseVector rowVec = new SparseVector();
                for (int i = 0; i < length; i++)
                {
                    int colKey = br_colval.ReadInt32();
                    double colValue = br_colval.ReadDouble();
                    rowVec[colKey] = colValue;
                }
                matrix[rowID] = rowVec;
                rowIdxFrom = rowIdxto;
                if (rowID % 10000 == 0)
                {
                    Console.WriteLine("reading matrix {0} rowID {1}", file, rowID);
                }
            }

            sr_IDrow.Close();
            br_colval.Close();

        }

        //write
        public void store(string file)
        {

            FileStream fs_IDrow = File.Create(file + ".IDrow");
            FileStream fs_colval = File.Create(file + ".colval");

            StreamWriter sw_IDrow = new StreamWriter(fs_IDrow);
            BinaryWriter bw_colval = new BinaryWriter(fs_colval);

            int count = 0;

            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                SparseVector rowVec = kvp.Value;
                count = count + rowVec.getCount();
                sw_IDrow.WriteLine(kvp.Key + "\t" + count);
                foreach (KeyValuePair<int, double> kvprow in rowVec.vector)
                {
                    bw_colval.Write(kvprow.Key);
                    bw_colval.Write(kvprow.Value);
                }
            }
            sw_IDrow.Close();
            bw_colval.Close();
        }

        public void storeFull(string file, int K)
        {
            FileStream fs = File.Create(file);
            StreamWriter sw = new StreamWriter(fs);

            foreach (KeyValuePair<int, SparseVector> kvp in matrix)
            {
                int rowID = kvp.Key;
                SparseVector rowVec = kvp.Value;

                sw.Write(rowID);
                for (int i = 0; i < K; i++) //from 0
                {
                    sw.Write("\t" + rowVec[i]);
                }
                sw.WriteLine();
            }

            sw.Close();
        }

    }
}
