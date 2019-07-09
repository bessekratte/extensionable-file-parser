package pl.britenet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.britenet.assembly.Assembler;
import pl.britenet.exception.FormatNotSupportedExcepion;
import pl.britenet.properties.PropertyResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Application {


    private static Path filePath;
    private static int bufferSize;
    private static String extension;
    private final static Logger logger = LoggerFactory.getLogger(Application.class);
    @Autowired
    private List<Assembler> assemblers;

    public static void main(String[] args) throws IOException {

        if (args.length < 1)
            throw new IOException("U need to define path to file in args");
        filePath = Paths.get(args[0]);

        bufferSize = args.length > 1 ?
                Integer.parseInt(args[1]) :
                Integer.parseInt(PropertyResolver.getProperty("default.buffer"));

        extension = args[0].substring(args[0].lastIndexOf("."));
        SpringApplication.run(Application.class, args);
    }

    private void parseFile() throws IOException {
        Assembler assembler = assemblers.stream()
                .filter(x -> x.getExtension().equals(extension))
                .findAny()
                .orElseThrow(FormatNotSupportedExcepion::new);
        assembler.parseFile(filePath, bufferSize);
    }

    @PostConstruct
    public void start() throws IOException {
        logger.info("Selected buffer = " + bufferSize);
        parseFile();
    }
}
