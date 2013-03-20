class WordSearcher
	def WordSearch(size, filename, term, runs)
		puts("At #{size} nodes:")
	
		list = Array.new(size)
		list = fillList(filename, list, size)
		
		sortStartTime = Time.now
		list = insertionSortList(list)
		sortFinishTime = Time.now
		sortTime = sortFinishTime - sortStartTime
		puts("Sort time: #{sortTime} seconds")
		
		searchStartTime = Time.now
		for i in 0..runs
			index = searchList(list, term.downcase, 0, size - 1)
		end
		searchFinishTime = Time.now
		searchTime = searchFinishTime - searchStartTime
		puts("Search time: #{searchTime} seconds")
		
		puts("#{term} at #{index}")
	end
	
	def fillList(filename, list, size)
		f = File.open(filename)
		f_lines = f.read.split("\n")
		
		ls = f_lines[0..size-1]
		res = []
		until ls.length == 0
			randInd = rand(ls.length)
			res << ls[randInd]
			ls.delete(ls[randInd])
		end
		res
	end
	
	def insertionSortList(l)
		list = l
		for i in 0..list.length - 1
			valueToInsert = list[i]
			hole = i
			while hole > 0 and isOutOfOrder(list[hole - 1], valueToInsert)
				list[hole] = list[hole - 1]
				hole -= 1
			end
			list[hole] = valueToInsert
		end
		list
	end
	
	def isOutOfOrder(firstTerm, secondTerm)
		firstList = []
		firstList << firstTerm
		firstList << secondTerm
		secondList = []
		secondList << firstList[0]
		secondList << firstList[1]
		secondList.sort!
		outOfOrder = false
		if firstList != secondList
			outOfOrder = true
		end
		outOfOrder
	end
	
	def searchList(list, term, start, fin) 
		if start == fin or fin - start == 1
			if list[start] == term
				return start
			elsif list[fin] == term
				return fin
			else
				return -1
			end
		end 
		
		mid = ((fin - start) / 2) + start
		if term == list[mid]
			return mid
		end
		
		if isOutOfOrder(list[mid], term)
			i = searchList(list, term, start, mid)
		else
			i = searchList(list, term, mid, fin)
		end
		i
	end
end

instance = WordSearcher.new
instance.WordSearch(100, "bigwords.txt", "a", 10000000)