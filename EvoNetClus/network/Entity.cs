using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using EvoNetClus.dataStruct;

namespace EvoNetClus.network
{
    public class Entity
    {
        public Dictionary<int, string> IDHT;
        public Dictionary<string, int> nameHT;
        public Dictionary<int, double> IDcount;

        public SparseVector IDcountVec;
        public string EntityName;
        public int EntityID;

        public Entity()
        {
            IDHT = new Dictionary<int, string>();
            nameHT = new Dictionary<string, int>();
            IDcount = new Dictionary<int, double>();
        }

        public void copy(Entity ent)
        {
            if (ent != null)
            {
                foreach (KeyValuePair<int, string> kvp in ent.IDHT)
                {
                    IDHT.Add(kvp.Key, kvp.Value);
                }

                foreach (KeyValuePair<string, int> kvp in ent.nameHT)
                {
                    nameHT.Add(kvp.Key, kvp.Value);
                }
                foreach (KeyValuePair<int, double> kvp in ent.IDcount)
                {
                    IDcount.Add(kvp.Key, kvp.Value);
                }
            }
            else
            {
                Console.WriteLine("Warning: copy null object");
                Console.ReadLine();
            }
        }

        public void loadData(string file, bool isWithID)
        {
            FileStream fs = File.OpenRead(file);
            StreamReader sr = new StreamReader(fs, Encoding.UTF8);

            string line = "";
            int lineNum = 0;

            while ((line = sr.ReadLine()) != null)
            {
                string[] strList;
                strList = line.Split("\t".ToCharArray());
                int len = strList.Length;

                lineNum++;

                int ID;
                string name;
                double count;
                if (isWithID)
                {
                    ID = int.Parse(strList[0]);
                    name = strList[1];
                    count = double.Parse(strList[2]);
                }
                else
                {
                    ID = lineNum;
                    name = strList[0];
                    count = double.Parse(strList[1]);
                }
                if (ID == 3815)
                    Console.WriteLine(name);

                IDHT.Add(ID, name);
                IDcount.Add(ID,count);
                if (!nameHT.ContainsKey(name))
                {
                    nameHT.Add(name, ID);
                }
            }
        }


        //getNamebyID
        public string this[int ID]
        {
            get
            {
                if (IDHT.ContainsKey(ID))
                    return IDHT[ID];
                else
                    return null;
            }
        }

        //getIDbyName
        public int this[string name]
        {
            get
            {
                if (nameHT.ContainsKey(name))
                    return nameHT[name];
                else
                    return -1;
            }
        }

        //add items in batch mode
        public void additems(Entity ent)
        {
            foreach (KeyValuePair<int, string> kvp in ent.IDHT)
            {
                if (!IDHT.ContainsKey(kvp.Key))
                {
                    IDHT.Add(kvp.Key, kvp.Value);
                    nameHT.Add(kvp.Value, kvp.Key);
                }
                if (IDcount.ContainsKey(kvp.Key))
                {
                    IDcount[kvp.Key] += ent.IDcount[kvp.Key];
                }
                else
                {
                    IDcount.Add(kvp.Key, ent.IDcount[kvp.Key]);
                }
            }
        }
    }
}
