import org.omg.CORBA.INTERNAL;

public class Day2 {
    public static void main(String args[]){
        String s = "5048\t177\t5280\t5058\t4504\t3805\t5735\t220\t4362\t1809\t1521\t230\t772\t1088\t178\t1794\n" +
                "6629\t3839\t258\t4473\t5961\t6539\t6870\t4140\t4638\t387\t7464\t229\t4173\t5706\t185\t271\n" +
                "5149\t2892\t5854\t2000\t256\t3995\t5250\t249\t3916\t184\t2497\t210\t4601\t3955\t1110\t5340\n" +
                "153\t468\t550\t126\t495\t142\t385\t144\t165\t188\t609\t182\t439\t545\t608\t319\n" +
                "1123\t104\t567\t1098\t286\t665\t1261\t107\t227\t942\t1222\t128\t1001\t122\t69\t139\n" +
                "111\t1998\t1148\t91\t1355\t90\t202\t1522\t1496\t1362\t1728\t109\t2287\t918\t2217\t1138\n" +
                "426\t372\t489\t226\t344\t431\t67\t124\t120\t386\t348\t153\t242\t133\t112\t369\n" +
                "1574\t265\t144\t2490\t163\t749\t3409\t3086\t154\t151\t133\t990\t1002\t3168\t588\t2998\n" +
                "173\t192\t2269\t760\t1630\t215\t966\t2692\t3855\t3550\t468\t4098\t3071\t162\t329\t3648\n" +
                "1984\t300\t163\t5616\t4862\t586\t4884\t239\t1839\t169\t5514\t4226\t5551\t3700\t216\t5912\n" +
                "1749\t2062\t194\t1045\t2685\t156\t3257\t1319\t3199\t2775\t211\t213\t1221\t198\t2864\t2982\n" +
                "273\t977\t89\t198\t85\t1025\t1157\t1125\t69\t94\t919\t103\t1299\t998\t809\t478\n" +
                "1965\t6989\t230\t2025\t6290\t2901\t192\t215\t4782\t6041\t6672\t7070\t7104\t207\t7451\t5071\n" +
                "1261\t77\t1417\t1053\t2072\t641\t74\t86\t91\t1878\t1944\t2292\t1446\t689\t2315\t1379\n" +
                "296\t306\t1953\t3538\t248\t1579\t4326\t2178\t5021\t2529\t794\t5391\t4712\t3734\t261\t4362\n" +
                "2426\t192\t1764\t288\t4431\t2396\t2336\t854\t2157\t216\t4392\t3972\t229\t244\t4289\t1902";
        String tab[] = s.split("\n");//split at each newline
        int min, max;
        int sum = 0;//init sum at 0
        for (int i = 0;i<tab.length;i++){//loop over each line
            //give min the maximum integer value so at the end the real min value would be greater than this initial value
            //opposite for max
            min = Integer.MAX_VALUE; max = 0;
            for(String num: tab[i].split("\t")){//split at each "\t" to have each numbers
                int val = Integer.parseInt(num);//parse them to have the int value
                if(val > max) max = val;//if the value is greater than max put this value in max
                if(val < min) min = val;// same here but opposite
            }
            sum += max - min;//add the difference to se sum
        }
        System.out.println(sum);

        //**********************

        int sum2 = 0;
        for (int i = 0;i<tab.length;i++){//loop over each line
            String line[] = tab[i].split("\t");//split over each "\t" to have each number
            int num[] = new int[line.length];//create an array to store each number after they have been parsed
            for(int j = 0;j<line.length;j++){//loop over each number
                num[j] = Integer.parseInt(line[j]);//parse the number
                for(int k = 0;k<j;k++){//check over each precedent number if the current one divide it evenly
                    //if the rest in the euclidean division is 0 it means that the right number divide the left number
                    //so we need to check both combination
                    if(num[j]%num[k] == 0){// j%k
                        sum2 += num[j]/num[k];// j/k
                    }else if(num[k]%num[j] == 0){// k%j
                        sum2 += num[k]/num[j];// k/j
                    }
                }
            }
        }
        System.out.println(sum2);
    }
}
