# SKILL.md - Technical Standards & Execution Skills

## 1. Mandatory Code Header Comments
Every new or modified handwritten code file must include a header comment. You must preserve the field names, order, and strictly enforce the author name as "Takashi Oikawa".

### Required Fields and Order
Program Name:
Language:
Function:
Created:
Last Updated:
Author: Takashi Oikawa
AI: Cursor (version only if confirmed)
Memo:

### Language Specific Syntax Rules
- **Java:** Use `/* ... */` syntax.
- **Python:** Use `#` syntax.
- **HTML:** Use `` syntax.
- **SQL:** Use `--` syntax.

*Note: Preserve the existing "Created:" value when modifying files. Update "Last Updated:" with the current date.*

### Non-commentable Formats (e.g., Strict JSON)
- Do not insert comments into the original file to prevent breaking parsing or compilation.
- Create or update an adjacent metadata file named `<original-file-name>.meta.md`.
- Record the exact same header fields in that metadata file.

## 2. Verification & Reporting Skill
After implementation, you must verify the following before declaring success:
- Review the diff to confirm that only relevant files were changed and no unintended modifications were made.
- Run the existing build and relevant tests.
- Confirm required code headers are added/updated, and no file format is broken.

### Output Report Format
Provide a concise, minimal report containing:
1. Summary of changes & Changed files
2. Header comment status (Confirmed)
3. Assumptions made (if any)
4. Verification results & Remaining risks
Keep explanations minimal. Do not output large code blocks unless explicitly requested.