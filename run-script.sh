#1. Make symlink for your main project in /use/local

#2. create a temp symlink for the input resources of your project
#ln -s /usr/local/DOCX-Xhtml-Spring/src/main/resources/input/xhtml/ /tmp/

file="/tmp/Output.docx"

if [ -f "$file" ] ; then
    rm "$file"
fi

gradle test --tests "docx.converter.xHtmlToDocxConverterTest"

mv /tmp/Output.docx /usr/local/DOCX-Xhtml-Spring/src/main/resources/output/docx/
