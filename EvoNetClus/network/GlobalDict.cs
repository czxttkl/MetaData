using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EvoNetClus.network
{
    public class GlobalDict
    {
        public Entity[] entityList;

        public GlobalDict(int n)
        {
            entityList = new Entity [n];
            for (int i = 0; i < n; i++)
            {
                entityList[i] = new Entity();
            }
        }

        public void addDict(int type, Entity curEntity)
        {
            entityList[type].additems(curEntity);
        }
    }
}
