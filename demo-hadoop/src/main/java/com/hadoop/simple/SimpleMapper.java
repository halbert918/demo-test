package com.hadoop.simple;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by admin on 2016/9/29.
 */
public class SimpleMapper extends Mapper<Object, Text, Text, IntWritable> {

    //存储每行数据
    private Text line = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        line = value;
        context.write(line, new IntWritable(1));
    }
}
