package com.betalpha.csvloader.tools;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * Created by liujia on 2017/6/29.
 */
public class JmxMetric {

    private final MetricRegistry metrics;
    private static final JmxMetric instance = new JmxMetric();

    private JmxMetric() {
        metrics = new MetricRegistry();
        JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).inDomain("csvloader").build();
        jmxReporter.start();
    }

    public static JmxMetric getInstance() {
        return instance;
    }

    public MetricRegistry getMetrics() {
        return metrics;
    }

    public void mark(String name) {
        metrics.meter(name).mark();
    }

    public void histogram(String name, long value) {
        metrics.histogram(name).update(value);
    }

    public void inc(String name) {
        metrics.counter(name).inc();
    }

    public void dec(String name) {
        metrics.counter(name).dec();
    }
}
