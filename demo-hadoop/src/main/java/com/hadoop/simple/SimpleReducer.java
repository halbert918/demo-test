package com.hadoop.simple;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by admin on 2016/9/29.
 */
public class SimpleReducer extends Reducer<Text, IntWritable, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        System.out.println("key = " + key.toString());
        int sum = 0;
        for(IntWritable value : values) {
            sum += value.get();
            System.out.println("value = " + sum);
        }
        context.write(key, new Text(String.valueOf(sum)));
    }
}
