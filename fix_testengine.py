import re

# In root pom, add junit-vintage-engine to run junit4 tests when surefire detects jupiter via camel
with open('pom.xml', 'r') as f:
    pom = f.read()

vintage_engine = """
            <dependency>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
                <version>5.9.2</version>
                <scope>test</scope>
            </dependency>
"""
pom = pom.replace("</dependencyManagement>", vintage_engine + "</dependencyManagement>")

with open('pom.xml', 'w') as f:
    f.write(pom)

with open('services/kagura-camel/pom.xml', 'r') as f:
    pom = f.read()

pom = pom.replace("</dependencies>", vintage_engine + "</dependencies>")
with open('services/kagura-camel/pom.xml', 'w') as f:
    f.write(pom)
