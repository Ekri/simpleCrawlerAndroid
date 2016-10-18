package com.maciega.bartosz.crawl.storage;

import java.util.List;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class StorageTransactionResult<T extends  Object> {
    private Throwable error;
    private T result;
    private List<T> resultsList;

    public StorageTransactionResult(Throwable error){
        this.error = error;
    }

    public StorageTransactionResult(T result){
        this.result = result;
    }

    public StorageTransactionResult(List<T> resultsList){
        this.resultsList = resultsList;
    }

    public T getResult(){
        return result;
    }

    public List<T> getResults(){
        return resultsList;
    }

    public boolean isSuccess(){
        return error == null;
    }

    public boolean hasResult(){
        return result != null;
    }

    public boolean hasResults(){
        return resultsList != null && !resultsList.isEmpty();
    }

}
