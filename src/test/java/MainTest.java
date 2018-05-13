import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {



    @Test
    public void delay() throws JsonProcessingException, InterruptedException {
        Md5Hash md5Hash = new Md5Hash("hehe", "hehe", 5);

        String s = JSON.toJSONString(MyResponse.createResponse(ResponseEnum.SUCC));
        System.out.println(md5Hash.toString());
        System.out.println(Character.isLetter('啊'));
        System.out.println("201706180810".substring("201706180810".length() - 4));
    }

//    @Test
//    public void testHdfs() throws URISyntaxException, IOException {
//        FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:8020"), new Configuration());
////        hdfs.mkdirs(new Path("/lfz"));
////        hdfs.delete(new Path("/lfz"), true);
//        hdfs.listStatus(new Path("/"));
//
//    }
//    @Test
//    public void testMR() throws IOException, ClassNotFoundException, InterruptedException {
//        Job job = Job.getInstance(new Configuration(), "wordCount");
//        job.setJarByClass();
//        //设置mapper
//        job.setMapperClass(MyMapper.class);
//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(LongWritable.class);
//        //设置reducer
//        job.setReducerClass(MyReducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(LongWritable.class);
//
//        //设置作业输入输出路径
//        FileInputFormat.setInputPaths(job, new Path("/"));
//        FileOutputFormat.setOutputPath(job,new Path("/"));
//
//        System.exit(job.waitForCompletion(true) ? 0 : -1);
//    }
//
//    //读取输入的文件
//    private static class MyMapper extends Mapper<LongWritable, Text,  , LongWritable> {
//        @Override
//        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//            String line = value.toString();
//            String[] words = line.split(" ");
//            for (String word : words) {
//                context.write(new Text(word), new LongWritable(1));
//            }
//        }
//    }
//
//    private static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
//        @Override
//        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
//            long sum = 0;
//            for (LongWritable value : values) {
//                sum = sum + value.get();
//            }
//            context.write(key, new LongWritable(sum));
//        }
//    }
}