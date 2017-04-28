package com.hadoop.simple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Created by admin on 2017/4/12.
 */
public class SimpleJob {

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        //设置hdfs的通讯地址
//        conf.set("fs.defaultFS", "hdfs://10.1.95.197:8020");
//        //设置RN的主机
//        conf.set("yarn.resourcemanager.hostname", "10.1.95.197");

        //这句话很关键
//        conf.set("mapred.job.tracker", "10.1.10.203:9000");
        String home = System.getenv("test");
        String[] ioArgs=new String[]{"input","output"};
        String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();

        if (otherArgs.length != 2) {
            System.err.println("Usage: Data Deduplication <in> <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "wData Deduplication");
        job.setJarByClass(SimpleJob.class);

        //设置Map和Reduce处理类
        job.setMapperClass(SimpleMapper.class);
        job.setReducerClass(SimpleReducer.class);

        //设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //设置输入和输出目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
