import pandas as pd

file_path = 'games.csv'

columns_of_interest = ["Tags", "Name", "Price", "Recommendations", "Peak CCU"]

df = pd.read_csv(file_path, usecols=columns_of_interest)

df_sorted = df.sort_values(by="Peak CCU", ascending=False)

df_top_1000 = df_sorted.head(1000)

output_file_path = 'gamesmodified.csv'

df_top_1000.to_csv(output_file_path, index=False)

print(f"Top 1000 games sorted by Peak CCU have been saved to {output_file_path}")
