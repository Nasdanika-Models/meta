```drawio-resource
../meta.drawio
```

[TOC levels=6]

# Nasdanika Meta Model

> **Draft.** This document and [`meta.xcore`](meta.xcore) belong in their own repository. They sit here only while the design settles.

An [Ecore](https://ecore.models.nasdanika.org/) micro-model of typed domain models - packages, classes, features, operations and the type references between them - positioned on the [Nasdanika model tower](https://nasdanika.com/models.html) directly above [nxcore](https://nxcore.models.nasdanika.org/).

About twenty classifiers. It is **loaded from** Ecore, Java, XML Schema, JSON Schema, JDBC metadata, Draw.io, Markdown and Excel, and **projected to** TypeScript, JSON Schema, Ecore and documentation. It is deliberately not a superset of any of its sources: whatever does not fit is dropped by design or carried in an `Annotation`, and the loaders decide which.

It is a [Published Language](https://martinfowler.com/bliki/PublishedLanguage.html) in the Domain-Driven Design sense - a documented shared vocabulary that bounded contexts translate into and out of - with the part Evans could not have in 2003, which is that the vocabulary is machine checkable because the types and the documentation are generated from it.

## Why

### Every format describes the same thing, slightly differently

A class with named, typed, multi-valued features is one of the most stable ideas in computing, and every ecosystem has reinvented its own spelling of it. The differences are real but small, and almost all of them are about the host language rather than about the domain.

The cost is not the spelling. It is that a bank's customer definition lives in nine formats at once - a DDL script, a JSON Schema, a COBOL copybook, an XSD from a partner, a Java class, a Confluence table, three spreadsheets - and nothing relates them. Each is a projection of a domain model that has never been written down. This model is the place to write it down once, and project.

### Documentation is an afterthought in most of them

This deserves care, because it is not uniformly true.

| Source | Documentation it carries |
|---|---|
| Ecore | one string, in a GenModel annotation detail |
| Java | nothing reflective - Javadoc lives in source comments, not in the type model |
| JDBC | `REMARKS`, one string per table or column, frequently empty |
| JSON Schema | `title` plus a `description` string |
| Protobuf | source comments, and only when descriptors are built to retain them |
| XML Schema | `xs:annotation/xs:documentation` - repeatable, language tagged, markup permitted |
| OpenAPI | `description` as CommonMark, plus `externalDocs` |
| OWL / RDFS | `rdfs:comment`, `skos:definition`, extensible, language tagged |
| **meta**, through nxcore | `documentation`, `docRef`, `docFormat`, `docContents`, `docSections` |

XML Schema, OpenAPI and OWL do respectably. The honest claim is narrower and more useful than "nobody supports documentation": **none of them treat documentation as structure.**

A single string cannot be divided into sections, cannot point at a resource that lives elsewhere, cannot declare its own format, and cannot say who owns it. The nxcore shape does all four - and because the [role](https://role.models.nasdanika.org/) floor extends `Section` and `Content` with `Undergoer`, every section of a document can answer *whose is this*. A definition with an owner is a different artifact from a definition without one, especially when it is wrong.

### Publishing, with diagrams, is the deliverable

A model that cannot be read by the people who own the vocabulary is a private notation. Every model in the tower generates its own site: one page per element, its documentation, its neighbours, and a generated diagram of the neighbourhood. A domain model published that way is a glossary that cannot drift from the types, because both come from one source.

That is the artifact a business stakeholder opens. Nobody has to hear the word "metamodel".

### Agents need structure, and the incumbent is a spreadsheet

The reason this matters more in 2026 than it did in 2016 is that something new is asking for the vocabulary.

An assistant reasoning over a bank's estate has to know what a Customer is, what an Account is, and how they relate. Today that context gets assembled by hand, per project, from whatever prose can be found. An agent given a declared, versioned, owned model answers from it and cites it; an agent given a DDL script and a wiki export infers, and inference is not a chain of custody.

And the competitor is not another modeling tool. It is **a spreadsheet and a Confluence table** - which win because they are free, everyone can already use them, and they never ask anyone to adopt anything. This model treats them as the input rather than the enemy: Excel is a first-class authoring surface, so the vocabulary can be captured where it already lives and projected from there.

## Authoring surfaces

One model, several front doors, chosen per audience rather than per author:

| Surface | For |
|---|---|
| [Excel](https://github.com/Nasdanika-Models/excel) | Business analysts and SMEs. The format the vocabulary is already in. |
| [Draw.io](https://drawio.models.nasdanika.org/) | Anyone who thinks in boxes and lines. The diagram is the source, not a picture of it. |
| Markdown | Definition-first authoring, where the prose matters more than the shape. |
| JSON and YAML | Machine authored, and the loaders' own output. |
| [Groovy DSL](https://docs.nasdanika.org/core/groovy/index.html) | Programmatic construction, prototypes and bulk edits. |
| Ecore, XSD, JSON Schema, JDBC | Not authoring at all - harvesting what already exists. |

The last row matters most. Most of a domain model is already written down somewhere; asking people to retype it is how modeling projects die.

## Generation targets

| Target | Produces |
|---|---|
| TypeScript | npm packages of interfaces plus runtime type information - see the [TypeScript model](https://typescript.models.nasdanika.org/) |
| Documentation | a site per package, one page per element, with generated diagrams |
| JSON Schema | validation, and tool schemas for agents |
| Ecore | a dynamic EPackage, so every existing EMF tool works on a model authored here without generating Java |
| SQL DDL, Java, Python | on demand - the projection is the easy part once the model exists |

The Ecore target is the quiet one and possibly the most useful: it means a metamodel authored in a spreadsheet can be validated, compared, diagrammed, exported to Neo4j and serialized to EMF JSON by tooling that already exists.

## Specializations per target

A projection sometimes needs to say something the neutral model should not carry. `JavaClass extends Class` adds a package name and a superclass; a persistence projection adds a table name; a TypeScript projection adds nothing at all, which is the point of the governing rule.

Those live in sub-models, not in annotations, when they are structural enough to deserve types - and in `Annotation`s when they are not. The neutral model stays neutral either way, which is what lets one vocabulary serve nine projections.

## Tower position

Directly on [nxcore](https://nxcore.models.nasdanika.org/), and nothing higher. What it needs from below is identity, documentation as structure, markers, and situation - all nxcore - and it needs nothing from the floors above.

That position is deliberate and load bearing. This model is the runtime foundation of every generated package: a browser application should not drag the governance floor in order to render a customer list, and an agent should not pay for lifecycle vocabulary in its context window on every call.

### Provenance, by construction

Every element carries nested markers, so a `Class` knows it came from a particular sheet of a particular spreadsheet at a particular commit, and a `Feature` knows which XSD element or which JDBC column produced it. When two sources disagree about what a Customer is - and they will - the model shows both and says where each came from.

### Situated: metamodel product lines

Classes and features are situated, so a metamodel occupies a region in a coordinate space: jurisdiction, language, tier, audience.

One banking metamodel therefore carries the Canadian and the United States variants together, with the features that differ marked by the coordinate they apply in. Generating for a coordinate emits what applies there and nothing else. That is a product line rather than a fork - the shared ninety percent stays shared, and the differences are visible as data instead of buried in the diff between two repositories.

The coordinate is a filter on the source and a receipt on the output. It is never a property of the generated artifact's contents, which is how one model stays one model.

### Shadowing at higher floors

The base model is deliberately thin. Richer variants extend it *and* the aspect mixins from the floors above:

```
meta.Class                                                    // portable, ships to browsers
sor.RecordLayout    extends meta.Class                        // physical storage
java.JavaClass      extends meta.Class                        // source language specifics
estate.ManagedClass extends meta.Class, Workable, Governed    // stewardship
```

Same object rather than a side table, so nothing needs joining, and the weight is paid only by consumers who take the specialization.

What that buys is worth stating concretely, because it is the difference between a schema and a domain model.

**A subject matter expert can talk for half an hour about the lifecycle of a merchant attribute on a credit card transaction.** When it is populated and by which system. What its values meant before the 2019 migration. Which downstream report breaks when it is null. Who to ask when it looks wrong. Which of the three systems that write it is authoritative on a Tuesday.

None of that is written down. It is not in the DDL, which has a column name and a type. It is not in Confluence, which has a page from 2021 whose author has left. It is not available to an agent in any form. It lives in one person's memory and leaves when they do.

With ownership from [role](https://role.models.nasdanika.org/), stages from [lifecycle](https://lifecycle.models.nasdanika.org/), normative statements from [requirements](https://requirements.models.nasdanika.org/) and remediation from [work](https://work.models.nasdanika.org/) attached to the same `Feature`, there is somewhere for all of it to go. And the loop is short enough to actually run:

> Set up the meeting. Capture the transcript. Ask an agent to update the spreadsheet. Publish.

The attribute gains an owner, a lifecycle stage, and a paragraph that says what it means. The next person to ask gets the answer from the model rather than from the expert's memory - and so does the next agent.

## Federated, in version control

There is no server and no central repository. A model is files in Git, and packages reference each other by URI, so a team owns its own vocabulary and points at somebody else's without either of them coordinating a release.

What that gives, which a catalog cannot:

- **Branching.** A proposed change to what a Customer is, on a branch, reviewed as a pull request, with the diff showing exactly which features moved.
- **History.** Not an audit log bolted on, but the same history the code has, with the same tools.
- **Ownership at the edges.** Nobody administers the whole graph. Business units publish their own packages and cross-reference by namespace.
- **Immutable snapshots.** A published version is what it was; "what did this mean in Q3" is an install rather than an archaeology project.

## Model overview

| Area | Classifiers |
|---|---|
| Base | `ModelElement` (on nxcore `NamedElement`), `Annotation` |
| Structure | `Package`, `Classifier`, `Class`, `DataType`, `Enum`, `EnumLiteral` |
| Members | `TypedElement`, `Feature`, `Attribute`, `Reference`, `Operation`, `Parameter` |
| Types | `TypeParameter`, `TypeReference`, `ClassifierReference`, `TypeParameterReference`, `PrimitiveTypeReference`, `UnionTypeReference`, `OpaqueTypeReference`, `PrimitiveKind` |

`Attribute.identifying` marks the attribute that gives an instance its identity, which is how a reference cites a target and, incidentally, how a DDD entity is distinguished from a value object. `Reference.containment` separates the tree edges from the graph edges. `Reference.opposite` and `keys` carry bidirectionality and target identity.

Metacircular by construction: a `Package` containing `Class`es describes `Package` and `Class`, so a runtime bootstraps from this model alone and needs no foreign metamodel.

## What is deliberately not modeled

The governing rule is that **if a class-based type system cannot express it, it is not a class here.** That keeps this at twenty classifiers rather than Ecore's fifty-three, of which a runtime loader uses seven.

So: no `volatile`, `transient`, `unsettable` or `resolveProxies` - EMF implementation concerns with no meaning in a browser. No feature maps, no generic type algebra, no factories. No subpackages, because neither TypeScript modules nor npm packages need the nesting. No `interface` flag, because whether a `Class` becomes an interface, a type alias or a class is a decision for the projection rather than a property of the model.

Everything else arrives through `Annotation`, `OpaqueTypeReference` or a sub-model. Loaders record what they cannot map rather than dropping it silently, so a round trip to the source stays possible even though this model is a projection.

## Landscape

| Camp | Examples | What it leaves undone |
|---|---|---|
| Industry data models | IBM Industry Models, Oracle FSDF, Teradata FSLDM | Content you license rather than a vocabulary you own; thousands of entities, and a poor implementation record |
| Standards | FIBO, BIAN, ISO 20022, ACORD | Genuinely good content, delivered as OWL files, PDFs and spreadsheets with no tooling. **Sources for this model, not rivals.** |
| Data catalogs | Collibra, Alation, Atlan | Organised around where data lives; business semantics stay thin, and the graph is centralized and rented |
| Semantic layers | dbt, Cube, AtScale, LookML | Analytics only - metrics, dimensions and joins, with no ownership, lifecycle or governance composition |
| Ontology platforms | Palantir Foundry | Has the whole thing and keeps it - the ontology does not leave the platform |
| Modeling tools | Sparx EA, erwin, MagicDraw | Desktop era, weak federation, and the output is a document |
| **The actual incumbent** | Excel and Confluence | Drifts within weeks, cannot be queried, and is invisible to an agent |

What is different here is not the class-and-feature part, which everybody has. It is four things none of the above combine:

**Composition.** Ownership, lifecycle, governance and work attach by inheritance from the floors below. A catalog has ownership; it has no governance metamodel that composes with an architecture metamodel that composes with a work metamodel.

**Federation without a server.** URI references across repositories, in Git, with no administrator of the whole.

**Documentation as structure, with owners.** Sections rather than a string, and sections that answer whose they are.

**Portability.** The model reaches a browser and an agent's context window as a typed npm package, without a JVM and without a platform. It is the ontology you can take with you.

## Resources

* [`meta.xcore`](meta.xcore) - the model source
* [nxcore](https://nxcore.models.nasdanika.org/) - identity, documentation, markers, situation
* [TypeScript model](https://typescript.models.nasdanika.org/) - the first projection
* [System of Records](https://sor.models.nasdanika.org/) - the physical data estate, a specialization of this vocabulary
* [Excel model](https://github.com/Nasdanika-Models/excel), [SQL model](https://github.com/Nasdanika-Models/sql) - authoring surfaces and harvesting
* [Nasdanika model tower](https://nasdanika.com/models.html)
