import matplotlib.pyplot as plt
import numpy as np
import csv

# DIGIT DATASET
# WEIGHT, GRADE, AVG LL, ACCURACY
# CLASSIFIED BY VARYING GRADE AND VARYING WEIGHT, FORCED TO USE ENTIRE BUDGET
# NORMAL LNB AVG LL = -508.19686354445224
# NORMAL LNB ACCURACY = 0.7801418439716312
fields = []
rows = np.empty((0,4))
with open("Generated Data\\digit-no-bottom-row-by-grade.txt", 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    fields = next(csvreader)
    for row in csvreader:
        rows = np.append(rows, np.array([row], dtype=float), axis=0)
# remove grades >5
rows1 = rows[rows[:, 1] == 1]
rows1 = np.append(rows1, rows[rows[:, 1] == 2], axis=0)
rows1 = np.append(rows1, rows[rows[:, 1] == 3], axis=0)
rows1 = np.append(rows1, rows[rows[:, 1] == 4], axis=0)
rows1 = np.append(rows1, rows[rows[:, 1] == 5], axis=0)
rows = rows1

figure, axis = plt.subplots(2, 2)
figure.suptitle('Digit dataset - Classified by varying grade and varying weight - Forced to use entire budget')

a = axis[0, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2])
b = axis[0, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2])
c = axis[0, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2])
d = axis[0, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2])
e = axis[0, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2])
axis[0,0].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 0].set_ylabel("Avg LL")
axis[0, 0].title.set_text("Weight vs Avg LL by Grade")

a = axis[0, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 3])
b = axis[0, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 3])
c = axis[0, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 3])
d = axis[0, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 3])
e = axis[0, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 3])
axis[0,1].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 1].set_ylabel("Accuracy")
axis[0, 1].title.set_text("Weight vs Accuracy by Grade")

# a = axis[1, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2])
# b = axis[1, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2])
# c = axis[1, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2])
# d = axis[1, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2])
# e = axis[1, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2])
# axis[1,0].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
# axis[1,0].set_xlabel("Weight")
# axis[1,0].set_ylabel("Avg LL")
# axis[1,0].title.set_text("Weight vs Avg LL by Grade")
# axis[1,0].axhline(y=-508.19686354445224, linestyle='--', label='Normal LNB')
# axis[1,0].legend(loc="best")
a = axis[1, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2], label='1')
b = axis[1, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2], label='2')
c = axis[1, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2], label='3')
d = axis[1, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2], label='4')
e = axis[1, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2], label='5')
f = axis[1,0].axhline(y=-508.19686354445224, linestyle='--', label='Normal LNB')
axis[1,0].set_xlabel("Weight")
axis[1,0].set_ylabel("Avg LL")
axis[1,0].title.set_text("Weight vs Avg LL by Grade")

axis[1,0].legend(loc="best")

a = axis[1, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 3])
b = axis[1, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 3])
c = axis[1, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 3])
d = axis[1, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 3])
e = axis[1, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 3])
axis[1,1].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[1,1].set_xlabel("Weight")
axis[1,1].set_ylabel("Accuracy")
axis[1,1].title.set_text("Weight vs Accuracy by Grade")
axis[1,1].axhline(y=0.7801418439716312, linestyle='--', label='Normal LNB')
axis[1,1].legend(loc="best")

plt.show()

# 3D PLOT FOR AVG LL
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 2])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('Average LL')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Forced to use entire budget\nAVERAGE LOG LIKELIHOOD\nLNB = -508.19686354445224')
plt.show()

# CONTOUR PLOT FOR AVG LL
figure, axis = plt.subplots()
# need to extract unique weights and grades
weights = np.unique(rows[:, 0])
grades = np.unique(rows[:, 1])
Z = np.zeros((len(grades), len(weights)))
# cycle grades (rows)
for i in range(len(grades)):
    # extract only the current grade
    temp = rows[rows[:, 1] == (i+1)]
    # cycle weights (columns)
    for j in range(len(weights)):
        Z[i, j] = temp[j, 2]
#axis.contour(weights, grades, Z)
CS = axis.contour(weights, grades, Z)
axis.clabel(CS, inline=True, fontsize=10)
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Forced to use entire budget\nAVERAGE LOG LIKELIHOOD')
plt.show()

# 3D PLOT FOR ACCURACY
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 3])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('Accuracy')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Forced to use entire budget\nACCURACY\nLNB = 0.7801418439716312')
plt.show()

# CONTOUR PLOT FOR ACCURACY
figure, axis = plt.subplots()
# need to extract unique weights and grades
weights = np.unique(rows[:, 0])
grades = np.unique(rows[:, 1])
Z = np.zeros((len(grades), len(weights)))
# cycle grades (rows)
for i in range(len(grades)):
    # extract only the current grade
    temp = rows[rows[:, 1] == (i+1)]
    # cycle weights (columns)
    for j in range(len(weights)):
        Z[i, j] = temp[j, 3]
CS = axis.contour(weights, grades, Z)
axis.clabel(CS, inline=True, fontsize=10)
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Forced to use entire budget\nACCURACY')
plt.show()

# DIGIT DATASET
# WEIGHT, GRADE, AVG LL, ACCURACY
# CLASSIFIED BY VARYING GRADE AND VARYING WEIGHT, USING IDEAL BUDGET
# NORMAL LNB AVG LL = -508.19686354445224
# NORMAL LNB ACCURACY = 0.7801418439716312
fields = []
rows = np.empty((0,4))
with open("Generated Data\\digit-with-bottom-row-by-grade.txt", 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    fields = next(csvreader)
    for row in csvreader:
        rows = np.append(rows, np.array([row], dtype=float), axis=0)

figure, axis = plt.subplots(2, 2)
figure.suptitle('Digit dataset - Classified by varying grade and varying weight - Using ideal budget')

a = axis[0, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2])
b = axis[0, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2])
c = axis[0, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2])
d = axis[0, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2])
e = axis[0, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2])
axis[0,0].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 0].set_ylabel("Avg LL")
axis[0, 0].title.set_text("Weight vs Avg LL by Grade")

a = axis[0, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 3])
b = axis[0, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 3])
c = axis[0, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 3])
d = axis[0, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 3])
e = axis[0, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 3])
axis[0,1].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 1].set_ylabel("Accuracy")
axis[0, 1].title.set_text("Weight vs Accuracy by Grade")

a = axis[1, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2])
b = axis[1, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2])
c = axis[1, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2])
d = axis[1, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2])
e = axis[1, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2])
axis[1,0].legend((a,b,c,d,e), ('1','2','3','4','5'))
axis[1,0].set_xlabel("Weight")
axis[1,0].set_ylabel("Avg LL")
axis[1,0].title.set_text("Weight vs Avg LL by Grade")
axis[1,0].axhline(y=-508.19686354445224, linestyle='--', label='Normal LNB')
axis[1,0].legend(loc="best")

a = axis[1, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 3])
b = axis[1, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 3])
c = axis[1, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 3])
d = axis[1, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 3])
e = axis[1, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 3])
axis[1,1].legend((a,b,c,d,e), ('1','2','3','4','5'))
axis[1,1].set_xlabel("Weight")
axis[1,1].set_ylabel("Accuracy")
axis[1,1].title.set_text("Weight vs Accuracy by Grade")
axis[1,1].axhline(y=0.7801418439716312, linestyle='--', label='Normal LNB')
axis[1,1].legend(loc="best")

plt.show()

# 3D PLOT FOR AVG LL
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 2])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('Average LL')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Using ideal budget\nAVERAGE LOG LIKELIHOOD\nLNB = -508.19686354445224')
plt.show()

# CONTOUR PLOT FOR AVG LL
figure, axis = plt.subplots()
# need to extract unique weights and grades
weights = np.unique(rows[:, 0])
grades = np.unique(rows[:, 1])
Z = np.zeros((len(grades), len(weights)))
# cycle grades (rows)
for i in range(len(grades)):
    # extract only the current grade
    temp = rows[rows[:, 1] == (i+1)]
    # cycle weights (columns)
    for j in range(len(weights)):
        Z[i, j] = temp[j, 2]
CS = axis.contour(weights, grades, Z)
axis.clabel(CS, inline=True, fontsize=10)
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Using ideal budget\nAVERAGE LOG LIKELIHOOD')
plt.show()

# 3D PLOT FOR ACCURACY
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 3])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('Accuracy')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Using ideal budget\nACCURACY\nLNB = 0.7801418439716312')
plt.show()

# CONTOUR PLOT FOR ACCURACY
figure, axis = plt.subplots()
# need to extract unique weights and grades
weights = np.unique(rows[:, 0])
grades = np.unique(rows[:, 1])
Z = np.zeros((len(grades), len(weights)))
# cycle grades (rows)
for i in range(len(grades)):
    # extract only the current grade
    temp = rows[rows[:, 1] == (i+1)]
    # cycle weights (columns)
    for j in range(len(weights)):
        Z[i, j] = temp[j, 3]
CS = axis.contour(weights, grades, Z)
axis.clabel(CS, inline=True, fontsize=10)
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.title.set_text('Digit dataset - Classified by varying grade and varying weight - Using ideal budget\nACCURACY')
plt.show()