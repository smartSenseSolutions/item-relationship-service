const fs = require("fs");
const path = require("path");
const PATH_TO_MD_FILES = "../../target/generated-docs/";

fs.readdirSync(PATH_TO_MD_FILES).forEach((file) => {
  if (path.extname(file) === ".md") {
    fs.readFile(PATH_TO_MD_FILES + file, "utf8", (err, data) => {
      if (err) throw err;

      const lines = data.split("\n");
      const output = [];
      let inCodeBlock = false;

      for (let i = 0; i < lines.length; i++) {
        // when line starts from _ and end with _ script will remove it (MD036 - Emphasis used instead of a heading)
        if (lines[i].startsWith("_") || lines[i].endsWith("_")) {
          output.push(lines[i].replace("_", "").replace("_", ""));
        // fix for MD033 - Inline HTML
        } else if (lines[i].startsWith("#### <") || lines[i].startsWith("##### <")) {
          output.push(lines[i].replace(" <", " ").replace(">", ""));
        // fix for MD040/fenced-code-language: Fenced code blocks should have a language specified, put bash as default
        } else if (lines[i].startsWith("```")) {
          if (inCodeBlock) {
            // end of block, just put ``` again
            inCodeBlock = false;
            output.push(lines[i]);
          } else {
            inCodeBlock = true;
            if (lines[i + 1].startsWith("@startjson")) {
              output.push("");
              output.push("```json");
            } else if (!lines[i].endsWith("yaml") && !lines[i].endsWith("json")) {
              output.push("```bash");
            } else {
              output.push(lines[i]);
            }
          }
        } else if (lines[i].startsWith("@startjson") || lines[i].startsWith("skinparam")) {
          // do not put it to md
        } else {
          output.push(lines[i]);
        }

      }

      fs.writeFile(
        `${PATH_TO_MD_FILES}${file}`,
        output.join("\n"),
        "utf8",
        (err) => {
          if (err) throw err;
          console.log(
            `successfully emphasis used instead of heading ${file}`
          );
        }
      );
    });
  }
});
