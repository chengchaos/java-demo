package com.example.nio.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/6/2021 3:50 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DemoPosition {

    private static Logger logger = LoggerFactory.getLogger(DemoPosition.class);

    private String filePath = "C:\\works\\temp\\gc.log";
    private String outputPath = "C:\\works\\temp\\gc2.log";

    public void demoPosition() throws IOException {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
             FileChannel fileChannel = randomAccessFile.getChannel()
        ) {
            // Set the file position.
            randomAccessFile.seek(1000);

            // this will print 1000
            logger.info("file pos: {}", fileChannel.position());

            // change the position using RrandomAccessFile object
            randomAccessFile.seek(500);

            // this will print 500
            logger.info("file pos: {}", fileChannel.position());

            // change the position using FileChannel object.
            fileChannel.position(200L);

            // then it will print 200
            logger.info("file pos: {}", randomAccessFile.getFilePointer());

        }
    }

    private void copy1(FileChannel src, WritableByteChannel dest) throws IOException {
        src.transferTo(0, src.size(), dest);
    }

    private void copy2(FileChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            buffer.clear();
        }
    }

    private void copy3(FileChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }
    /**
     * https://www.jianshu.com/p/ab7044548e08
     */
    public void fileChannelReadWrite() {
        try (
                RandomAccessFile inputFile = new RandomAccessFile(filePath, "rw");
                RandomAccessFile outputFile = new RandomAccessFile(outputPath, "rw");
                FileChannel inputFileChannel = inputFile.getChannel();
                FileChannel outputFileChannel = outputFile.getChannel()
        ) {
            long size = inputFileChannel.size();
            int length = (int) inputFile.length();

            logger.info("file size => {}, length => {}", size, length);
            ByteBuffer byteBuffer = ByteBuffer.allocate(length)
                    .order(ByteOrder.LITTLE_ENDIAN);
            inputFileChannel.read(byteBuffer);

            CharBuffer charBuffer = byteBuffer.asCharBuffer();

            charBuffer.flip();

            logger.info("byteBuffer => {}", byteBuffer);
            byteBuffer.flip();
            logger.info("byteBuffer => {}", byteBuffer);

            CharBuffer decode = StandardCharsets.UTF_8.decode(byteBuffer);

            String text = new String(decode.array());
            logger.info("text => {}", text);

        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) {
        new DemoPosition().fileChannelReadWrite();

    }
}
