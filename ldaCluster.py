import sys
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.decomposition import LatentDirichletAllocation
from scipy.sparse.csr import csr_matrix
import numpy as np

if __name__ == '__main__':
    # arr = np.array([[0, 1, 0, 2, 0], [1, 1, 0, 2, 0], [2, 0, 5, 0, 0]])
    tagCountData = open('tagCountData.txt', 'r')
    paperTagVector = tagCountData.read().split(" ")

    nTopics = int(paperTagVector[0])
    tagNum = int(paperTagVector[1])
    paperNum = int(paperTagVector[2])
    allPaperTags = np.empty(shape=[0, tagNum])
    i = 3
    for paper in range(0, paperNum):
        paperTags = np.zeros(tagNum)
        for tag in range(0, tagNum):
            paperTags[tag] = int(paperTagVector[i])
            i = i + 1
        allPaperTags = np.append(allPaperTags, [paperTags], axis=0)

    #print("paperTagData: ")
    #print(allPaperTags)
    b = csr_matrix(allPaperTags)
    #print("csr_matrix: ")
    #print(b)

    lda = LatentDirichletAllocation(n_components=nTopics,
                                    learning_offset=50.,
                                    random_state=0)
    docres = lda.fit_transform(b)
    print(docres)
    print(lda.components_)
    # for i in docres:
    #     for j in i:
    #         print(j, end=" ")
    #     print("")
    # for i in lda.components_:
    #     for j in i:
    #         print(j, end=" ")
    #     print("")
