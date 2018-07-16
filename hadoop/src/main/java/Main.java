import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/12 下午9:23
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration(), "wordCount");
        //设置jar包主类
        job.setJarByClass(Main.class);
        //设置mapper
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //设置reducer
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //在本地先进行一次reduce，减少将数据发送量
        job.setCombinerClass(MyReducer.class);

        //设置partitioner
        //暂时没用到
//        job.setPartitionerClass(MyPartitioner.class);
//        job.setNumReduceTasks(2);

        //设置作业输入输出路径，注意输出文件是不能事先存在的，输出文件是一个文件夹，里面有结果和运行结束状态
        FileInputFormat.setInputPaths(job, new Path("hdfs://localhost:8020/access.log"));
        FileOutputFormat.setOutputPath(job,new Path("hdfs://localhost:8020/output"));

        System.exit(job.waitForCompletion(true) ? 0 : -1);
    }
}
