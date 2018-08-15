package com.hexagon.boot.generator.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public abstract class JavaCodeFormattingUtil {

    public static String format(String code) {
        Map m = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
        m.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
        m.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
        m.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
        m.put(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "160");
        m.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
        CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(null);

        TextEdit textEdit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, code, 0, code.length(), 0, null);
        IDocument doc = new Document(code);
        try {
            textEdit.apply(doc);
            return doc.get();
        } catch (MalformedTreeException | BadLocationException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static class Formatter extends StringWriter {

        private final Writer out;

        public Formatter(Writer out) {
            this.out = out;
        }

        @Override
        public void flush() {
            try {
                out.write(JavaCodeFormattingUtil.format(this.toString()));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() throws IOException {
            out.write(JavaCodeFormattingUtil.format(this.toString()));
            out.close();
        }
    }
}
