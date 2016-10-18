package com.maciega.bartosz.crawl.storage;

import com.maciega.bartosz.crawl.model.Url;

import java.util.List;

/**
 * Created by Bartosz on 2016-10-18.
 */

public interface UrlStorage extends Storage {
    Url get(String primaryKey);
}
