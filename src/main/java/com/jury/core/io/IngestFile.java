package com.jury.core.io;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.entity.dao.DaoTemplate;
import com.jury.core.entity.transformer.FileTransformer;
import com.jury.core.session.Session;

import java.io.File;
import java.io.IOException;

public class IngestFile<T extends DatabaseObject, PK> {

    private FileTransformer<T> fileTransformer;
    private DaoTemplate<T,PK> dao;

    public IngestFile(FileTransformer<T> fileTransformer, DaoTemplate<T, PK> dao) {
        this.fileTransformer = fileTransformer;
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
                T object = fileTransformer.consume(line);
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
