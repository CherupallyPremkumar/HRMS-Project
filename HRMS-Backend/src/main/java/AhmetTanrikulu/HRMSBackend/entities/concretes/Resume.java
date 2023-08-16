package AhmetTanrikulu.HRMSBackend.entities.concretes;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.fileupload.FileItem;

import javax.persistence.*;
import java.io.File;

@Entity
@Setter
@Getter
public class Resume extends User {
    @Lob
    private File Resumebytes;
    private String resumeName;
    private long size;

}
