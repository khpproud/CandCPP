package com.patrick.rxstudy11.example;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 *  파일 기반 캐싱 예제
 * @param <R>
 */
public class FileCacheObservableTransformer<R> implements ObservableTransformer<R, R> {
    private final String fileName;
    private final Context context;

    private FileCacheObservableTransformer(String fileName, Context context) {
        this.fileName = fileName;
        this.context = context;
    }

    public static <R> FileCacheObservableTransformer<R> cacheToLocalFileNamed(String fileName,
                                                                              Context context) {
        return new FileCacheObservableTransformer<>(fileName, context);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return readFromFile()
                .onExceptionResumeNext(
                        upstream.take(1)
                        .doOnNext(this::saveToFile)
                );
    }

    private Observable<R> readFromFile() {
        return Observable.create(emitter -> {
            ObjectInputStream input = null;
            try {
                final FileInputStream fileInputStream = new FileInputStream(getFileName());
                input = new ObjectInputStream(fileInputStream);
                R foundObject = (R) input.readObject();
                emitter.onNext(foundObject);
            } catch (IOException e) {
                emitter.onError(e);
            } finally {
                if (input != null)
                    input.close();
                emitter.onComplete();
            }
        });
    }

    private void saveToFile(R r) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(getFileName());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(r);
        } finally {
            if (objectOutputStream != null)
                objectOutputStream.close();
        }
    }

    private String getFileName() {
        return context.getFilesDir().getAbsolutePath() + File.separator + fileName;
    }
}
