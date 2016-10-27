package com.hadoop.simple;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by admin on 2016/9/29.
 */
public class SimpleReducer extends Reducer<Object, Text, Text, IntWritable> {

    @Override
    protected void reduce(Object key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        super.reduce(key, values, context);
    }
}
