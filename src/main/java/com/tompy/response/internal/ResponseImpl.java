package com.tompy.response.internal;

import com.tompy.response.api.Response;
import com.tompy.response.api.ResponseBuilder;
import com.tompy.response.api.ResponseBuilderFactory;

public class ResponseImpl implements Response, Comparable<Response> {
    private static long counter = 0;
    private long sequence;
    private String source;
    private String text;

    private ResponseImpl(String source, String text) {
        this.source = source;
        this.text = text;
        sequence = counter++;
    }

    @Override
    public long getSequence() {
        return sequence;
    }

    @Override
    public void render() {
        System.out.println("[" + source + "]" + text);
    }

    public static ResponseBuilderFactory createBuilderFactory() { return ResponseImpl::createBuilder; }

    public static ResponseBuilder createBuilder() { return new ResponseImpl.ResponseBuilderImpl(); }

    @Override
    public int compareTo(Response other) { return (int)(other.getSequence() - this.getSequence()); }

    public static class ResponseBuilderImpl implements ResponseBuilder {
        private String source;
        private String text;

        @Override
        public ResponseBuilder source(String source) {
            this.source = source;
            return this;
        }

        @Override
        public ResponseBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public Response build() { return new ResponseImpl(source, text); }
    }

}