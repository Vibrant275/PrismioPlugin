package com.vibrant.prismio.settings;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.vibrant.prismio.highlighter.PsSyntaxHighlighter;
import com.vibrant.prismio.utils.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

final class ColorSettingsPage implements com.intellij.openapi.options.colors.ColorSettingsPage {

  private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
          new AttributesDescriptor("Keywords", PsSyntaxHighlighter.KEYWORD),
          new AttributesDescriptor("Type Keywords", PsSyntaxHighlighter.TYPE_KEYWORD),
          new AttributesDescriptor("Identifiers", PsSyntaxHighlighter.IDENTIFIER),
          new AttributesDescriptor("Numbers", PsSyntaxHighlighter.NUMBER),
          new AttributesDescriptor("Strings", PsSyntaxHighlighter.STRING),
          new AttributesDescriptor("Operators", PsSyntaxHighlighter.OPERATOR),
          new AttributesDescriptor("Separators (Braces, Parentheses)", PsSyntaxHighlighter.SEPARATOR),
          new AttributesDescriptor("Line Comments", PsSyntaxHighlighter.LINE_COMMENT),
          new AttributesDescriptor("Block Comments", PsSyntaxHighlighter.BLOCK_COMMENT),
          new AttributesDescriptor("Bad Character", PsSyntaxHighlighter.BAD_CHARACTER)
  };

  @Override
  public Icon getIcon() {
    return Icons.FILE;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new PsSyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return """
        // Prismio Language Demo
        /* Multi-line comment
           demonstrating syntax highlighting */
        
        import prismio.io
        
        extern fn println(msg: String)
        extern fn println_int(value: Int)
        
        struct Point {
            x: Int,
            y: Int
        }
        
        enum Direction {
            North,
            South,
            East,
            West
        }
        
        fn calculate(a: Int, b: Int) -> Int {
            let mut result = 0
            let sum = a + b
            let diff = a - b
            
            if (sum > 10) {
                result = sum * 2
            } else {
                result = diff / 2
            }
            
            return result
        }
        
        fn fibonacci(n: Int) -> Int {
            if (n <= 1) {
                return n
            } else {
                let a = fibonacci(n - 1)
                let b = fibonacci(n - 2)
                return a + b
            }
        }
        
        fn main() {
            let x = 42
            let y = 3.14
            let name = "Prismio"
            let flag = true
            let ch = 'A'
            
            let result = calculate(10, 5)
            println_int(result)
            
            let mut counter = 0
            while (counter < 10) {
                counter = counter + 1
            }
            
            for (i in range) {
                println_int(i)
            }
        }
        """;
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return null;
  }

  @Override
  public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
    return DESCRIPTORS;
  }

  @Override
  public ColorDescriptor @NotNull [] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Prismio";
  }
}