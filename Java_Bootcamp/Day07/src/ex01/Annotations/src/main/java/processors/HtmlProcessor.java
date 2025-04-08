package processors;

import annotations.HtmlForm;
import annotations.HtmlInput;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;


@SupportedAnnotationTypes({"annotations.HtmlForm", "annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            try {
                generateHtmlForm((TypeElement) element);
            } catch (IOException e) {
                error("Failed to generate HTML form: " + e.getMessage());
            }
        }
        return true;
    }

    private void generateHtmlForm(TypeElement element) throws IOException {
        HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
        String filrName = htmlForm.fileName();

        StringBuilder builder = new StringBuilder()
                .append(String.format("<form action=\"%s\" method=\"%s\">\n",
                        htmlForm.action(), htmlForm.method()));
        element.getEnclosedElements().stream()
                .filter(e -> e.getKind().isField())
                .forEach(field -> {
                    HtmlInput input = field.getAnnotation(HtmlInput.class);
                    if (input != null) {
                        builder.append(String.format("\t<input type=\"%s\" name=\"%s\" placeholder=\"%s\">\n",
                                input.type(), input.name(), input.placeholder()));
                    }
                });
        builder.append("\t<input type=\"submit\" value=\"Send\">\n</form>");
        writeToFile(filrName, builder.toString());
    }

    private void writeToFile(String filename, String content) throws IOException {
        FileObject file = filer.createResource(StandardLocation.CLASS_OUTPUT, "", filename);
        try (Writer writer = file.openWriter()) {
            writer.write(content);
        }
    }

    private void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }
}
