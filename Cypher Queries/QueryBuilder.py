
import pandas as pd

def remove_non_ascii(text):
    return ''.join(char for char in text if ord(char) < 128)

def clean_tags(tags):
    if isinstance(tags, str):
        return ','.join(remove_non_ascii(tag) for tag in tags.split(','))
    return '' 

file_path = 'gamesmodified.csv'
output_file_path = 'cypher_queries.txt'

df = pd.read_csv(file_path)

df['Name'] = df['Name'].apply(remove_non_ascii)

# Clean 'Tags' column
df['Tags'] = df['Tags'].apply(clean_tags)

with open(output_file_path, 'w', encoding='utf-8') as f:
    for index, row in df.iterrows():
        recom = row['Peak CCU'] / row['Recommendations'] if row['Recommendations'] != 0 else 0
        title = row['Name']

        tags = row['Tags'].split(',') if row['Tags'] else []
        price = "Free" if row['Price'] == 0 else "Paid"

        game_query = f'CREATE (g:Game {{title: "{title}", recom: {recom}}});\n'
        f.write(game_query)

        for tag in tags:
            tag = tag.strip()
            if tag:
                tag_query = f'MERGE (t:Tag {{name: "{tag}"}});\n'
                f.write(tag_query)
                has_tag_query = f'MATCH (g:Game {{title: "{title}"}}), (t:Tag {{name: "{tag}"}}) CREATE (g)-[:HAS_TAG]->(t);\n'
                f.write(has_tag_query)

        price_query = f'MERGE (p:Price {{type: "{price}"}});\n'
        f.write(price_query)
        if price == "Free":
            free_relationship_query = f'MATCH (g:Game {{title: "{title}"}}), (p:Price {{type: "Free"}}) CREATE (g)-[:HAS_PRICE]->(p);\n'
            f.write(free_relationship_query)

print(f"Cypher queries have been saved to {output_file_path}")
