import java.io.*;
import java.util.*;

public class runDemo {

    public static void main(String[] args) {
        new runDemo();
    }

    public runDemo(){

        File file1 = new File("new1.txt");
        File file2 = new File("new2.txt");

        String msg1 = read(file1);
        String msg2 = read(file2);

        CompireChar(msg1,msg2);

    }

    //此方法读取文件内容并返回字符串
    private String read(File file){

        InputStream dis = null;
        String msg = "";

        //获取输入流并写入字符串种
        try {
            dis = new FileInputStream(file);
            byte[] data = new byte[1024];

            int len = 0;
            while ((len = dis.read(data)) != -1){
                msg = msg + new String(data,0,len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回字符串
        return msg;
    }

    /*
    * 此方法对比同时出现字符的数量，并存入一个map中，其中map的key为字符，value为出现次数
    */
    private void CompireChar(String msg1, String msg2){

        Map<String,Integer> map1 = new HashMap<>();

        System.out.println("请选择分割模式，按照单词统计输入1并回车（此模式仅支持英文文章）；按照字统计输入2并回车（此方法支持中英文文章）");
        Scanner scanner = new Scanner(System.in);
        int flag = scanner.nextInt();
        System.out.println(flag);

        //此处分两种情况，一种是按照单词分，一种是按照单个字分
        /*
        大体逻辑：首先将其中一条字符串按照分类模式存入一个数组中，接下来遍历数组，如果该元素在另外一条字符串中也存在，那么
        接下来继续判断，如果map中没有这个字（词），那么就新建一条记录，并将value设置为1，表示出现一次
        否则先获取之前的value值，将这个值加一，删除原来的记录并重新将新的键值对赋值进去
        */
        if (flag == 1){

            String[] strs1 = msg1.split(" ");

            for (int i = 0; i < strs1.length; i++) {
                if (msg2.contains(strs1[i])){
                    String temp = strs1[i];
                    if (!map1.containsKey(temp)) {
                        map1.put(temp, 1);
                    } else {
                        int inttemp = map1.get(temp);
                        inttemp = inttemp + 1;
                        map1.remove(temp);
                        map1.put(temp, inttemp);
                    }
                }
            }

        }else if (flag == 2) {

            char[] chars1 = msg1.toCharArray();

            for (int i = 0; i < chars1.length; i++) {
                if (msg2.contains("" + chars1[i])) {
                    String temp = "" + chars1[i];
                    if (!map1.containsKey(temp)) {
                        map1.put(temp, 1);
                    } else {
                        int inttemp = map1.get(temp);
                        inttemp = inttemp + 1;
                        map1.remove(temp);
                        map1.put(temp, inttemp);
                    }
                }
            }
        }

        //调用排序方法，返回一个排序后的List对象，并打印其中的前十个
        List<Map.Entry<String,Integer>> keylist = mapSqrt(map1);
        for (int i = 0; i < 11; i++) {
            System.out.println(keylist.get(i).getKey() + " = " + keylist.get(i).getValue() );
        }
    }

    //排序方法
    private List<Map.Entry<String,Integer>> mapSqrt(Map<String, Integer> map){
        //首先将map内的对象存入一个list当中
        List<Map.Entry<String,Integer>> keylist = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        //接下来按照自定义规则排序，这里是按照value（也就是出现次数）进行排序
        Collections.sort(keylist, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o2.getValue().compareTo(o1.getValue()) > 0){
                    return 1;
                }else if (o2.getValue().compareTo(o1.getValue()) < 0){
                    return  -1;
                }else {
                    return 0;
                }
            }
        });

        return keylist;
    }

}
