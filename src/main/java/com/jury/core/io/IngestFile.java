package com.jury.core.io;

import com.jury.core.database.dao.DaoTemplate;
import com.jury.core.database.entity.DatabaseObject;
import com.jury.core.database.transformer.DboFileTransformer;

import java.io.File;
import java.io.IOException;

public class IngestFile<PK, T extends DatabaseObject<PK>> {

    private DboFileTransformer<T> dboFileTransformer;
    private DaoTemplate<PK, T> dao;

    public IngestFile(DboFileTransformer<T> dboFileTransformer, DaoTemplate<PK, T> dao) {
        this.dboFileTransformer = dboFileTransformer;
        this.dao = dao;
    }

    public Counter run(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist");
        }
        Counter c = new Counter();
        FileHandler.readWithAction(file, (line) -> {
            if (line == null) {
                return;
            }
            line = line.replace("\u0000","").replace("\\u0000","");
            if (line.isEmpty()) {
                return;
            }
            try {
                c.lines++;
                T object = dboFileTransformer.consume(line);
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
