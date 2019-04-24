package com.example.myscala002;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/20 0020 上午 10:45 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class FolderSearchTask extends RecursiveTask<Long> {


    private final Folder folder;
    private final String searchedWord;

    public FolderSearchTask(Folder folder, String searchedWord) {

        super();
        this.folder = folder;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {

        long count = 0L;
        List<RecursiveTask<Long>> forks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderSearchTask task = new FolderSearchTask(subFolder, searchedWord);
            forks.add(task);
            task.fork();
        }

        for (Document document :folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document, searchedWord);
            forks.add(task);
            task.fork();
        }

        for (RecursiveTask<Long> task: forks) {
            count = count + task.join();
        }

        return count;

    }
}

class Folder {

    public Folder[] getSubFolders() {
        return new Folder[]{};
    }

    public Document[] getDocuments() {
        return new Document[0];
    }
}

class Document {

}

class DocumentSearchTask extends RecursiveTask<Long> {

    private Document document;
    private final String searchedWord;

    public DocumentSearchTask(Document document, String searchedWord) {
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        return 0L;
    }
}
