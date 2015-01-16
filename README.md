Metada project

# Data clean steps

1. run `DataCleaner.java` to move data from paper collection to paper_clean collection
2. remove aaai in paper_clean collection. 
	```
	db.papers_clean.find({"venue":"AAAI"})
	```
3. run `ReferenceMatcher.java` in order to add the authors to each paper who cite the paper.