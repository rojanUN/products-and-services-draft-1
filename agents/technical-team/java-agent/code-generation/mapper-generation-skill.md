# Mapper Generation Skill

## Rules

- MapStruct `@Mapper(componentModel = "spring")`
- Separate methods: `toEntity`, `toResponse`, `toSummary`
- Ignore audit and internal fields on response mapping
- Map collections and Page types explicitly

## Template

See [../templates/mapper-template.md](../templates/mapper-template.md) and [../programming/model-mapper-skill.md](../programming/model-mapper-skill.md).
