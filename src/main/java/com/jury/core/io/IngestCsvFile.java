package com.jury.core.io;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.entity.dao.DaoTemplate;
import com.jury.core.entity.transformer.CsvTransformer;
import com.jury.core.session.Session;

import java.io.File;
import java.io.IOException;

public class IngestCsvFile<T extends DatabaseObject, PK> {

    private Session session;
    private CsvTransformer<T> csvTransformer;
    private DaoTemplate<T,PK> dao;

    public IngestCsvFile(Session session, CsvTransformer<T> csvTransformer, DaoTemplate<T, PK> dao) {
        this.session = session;
        this.csvTransformer = csvTransformer;
        this.dao = dao;
    }

    public Counter run(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist");
        }
        Counter c = new Counter();
        FileHandler.readWithAction(file, (line) -> {
            try {
                c.lines++;
                T object = csvTransformer.produce(line);
                c.objects++;
                dao.insert(object);
                c.success++;
            } catch (Exception e) {
                c.failure(e.getMessage());
            }
        });
        return c;
    }

}
