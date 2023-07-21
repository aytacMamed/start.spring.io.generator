package net.memmedli.web.generator.service;

import net.memmedli.web.generator.model.dto.ProjectConfigDto;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class GeneratorService {

    private final String BASE_DIRECTORY = "C:\\Users\\Acer\\Desktop\\";

    private final List<String> folders = List.of("model.dto", "model.validator", "model.entity", "service", "controller");

    public void generate(ProjectConfigDto dto) {
        String rootPath = BASE_DIRECTORY + dto.getName();
        File rootFolder = new File(rootPath);
        if (!createFolder(rootPath)) throw new RuntimeException("Could not create root folder");

        generatePom(dto, rootPath);

        String srcPath = rootPath + (File.separator + "src.main.java" + File.separator + dto.getRootPackage()).replace(".", File.separator);
        generateApplicationFile(dto, srcPath);

        generateStructure(dto,srcPath);



    }

    private void generateApplicationFile(ProjectConfigDto dto, String srcPath) {
        if (!createFolder(srcPath)) throw new RuntimeException("Could not create src folder");

        ClassPathResource resource = new ClassPathResource("Application.java");
        String upperCaseArtifact = dto.getArtifactId().substring(0, 1).toUpperCase() + dto.getArtifactId().substring(1);
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(srcPath + File.separator + upperCaseArtifact + "Application.java");
        ) {

            byte[] byteFile = is.readAllBytes();
            String stringFile = new String(byteFile);
            stringFile = stringFile.replace("Example", upperCaseArtifact);
            stringFile = stringFile.replace("srcpath", dto.getGroupId() + "." + dto.getArtifactId());
            os.write(stringFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generatePom(ProjectConfigDto dto, String rootPath) {
        ClassPathResource resource = new ClassPathResource("example_pom.xml");
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(rootPath + File.separator + "pom.xml");
        ) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model pom = reader.read(is);
            pom.setArtifactId(dto.getArtifactId());
            pom.setGroupId(dto.getGroupId());
            pom.setVersion(dto.getVersion());
            pom.setName(dto.getName());
            MavenXpp3Writer writer = new MavenXpp3Writer();
            System.out.println(pom.getGroupId());
            writer.write(os, pom);
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

private void generateStructure(ProjectConfigDto dto,String srcPath){
        String projectFolder = dto.getGroupId() + "." + dto.getArtifactId();

    for (String structure : folders){
        structure = structure.replace(".",File.separator);
        String path = srcPath + File.separator + structure;
        if (!createFolder(path)) throw new RuntimeException(String.format("Could not create %s folder",path));
    }

    for (String model : dto.getModels()){
        ClassPathResource resource = new ClassPathResource("Example.java");
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(srcPath + File.separator + "model/entity" + File.separator + model + ".java")
        ){
            String entityContent = new String(is.readAllBytes());
            entityContent = entityContent.replace("Example",model);
            entityContent = entityContent.replace("srcpath",projectFolder);
            os.write(entityContent.getBytes());
        }catch (Exception exception){
            exception.printStackTrace();
        }

         resource = new ClassPathResource("Controller.java");
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(srcPath + File.separator + "controller" + File.separator + model + "Controller.java")
        ){
            String entityContent = new String(is.readAllBytes());
            entityContent = entityContent.replace("Example",model);
            entityContent = entityContent.replace("srcpath",projectFolder);
            os.write(entityContent.getBytes());
        }catch (Exception exception){
            exception.printStackTrace();
        }

        resource = new ClassPathResource("ExampleDto.java");
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(srcPath + File.separator + "model/dto" + File.separator + model + "Dto.java")
        ){
            String entityContent = new String(is.readAllBytes());
            entityContent = entityContent.replace("Example",model);
            entityContent = entityContent.replace("srcpath",projectFolder);
            os.write(entityContent.getBytes());
        }catch (Exception exception){
            exception.printStackTrace();
        }

        resource = new ClassPathResource("ExampleValidator.java");
        try (InputStream is = resource.getInputStream();
             OutputStream os = new FileOutputStream(srcPath + File.separator + "model/validator" + File.separator + model + "Validator.java")
        ){
            String entityContent = new String(is.readAllBytes());
            entityContent = entityContent.replace("Example",model);
            entityContent = entityContent.replace("srcpath",projectFolder);
            os.write(entityContent.getBytes());
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }
}

    private boolean createFolder(String path) {
        File srcFolder = new File(path);
        return srcFolder.mkdirs();
    }
}
