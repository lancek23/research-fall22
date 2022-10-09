import matplotlib.pyplot as plt
import numpy as np
import csv

# SYNTHETIC DATASET - 100 INPUTS (101 PARAMETERS), 1000 TRAINING EXAMPLES, 1000 TEST EXAMPLES
# WEIGHT, GRADE, AVG LL, ACCURACY, KL-DIVERGENCE
# CLASSIFIED BY VARYING GRADE AND VARYING WEIGHT, FORCED TO USE ENTIRE BUDGET
# TRUE AVG LL FROM DATASET = -108.27602065828165
# NORMAL LNB AVG LL = -49.48419132188499
# NORMAL LNB ACCURACY = 1.0
# NORMAL LNB KL-DIVERGENCE = 0.05069808838177512
fields = []
rows = np.empty((0,5))
with open("Generated Data\\synthetic-no-bottom-row.txt", 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    fields = next(csvreader)
    for row in csvreader:
        rows = np.append(rows, np.array([row], dtype=float), axis=0)

figure, axis = plt.subplots(2, 2)
figure.suptitle('Synthetic dataset - Classified by varying grade and varying weight - Forced to use entire budget')

a = axis[0, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2])
b = axis[0, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2])
c = axis[0, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2])
d = axis[0, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2])
e = axis[0, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2])
axis[0,0].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 0].set_ylabel("Avg LL")
axis[0, 0].title.set_text("Weight vs Avg LL by Grade")

a = axis[0, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 4])
b = axis[0, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 4])
c = axis[0, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 4])
d = axis[0, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 4])
e = axis[0, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 4])
axis[0,1].legend((a,b,c,d,e), ('1','2','3','4','5'), loc="best")
axis[0, 1].set_ylabel("KL-Divergence")
axis[0, 1].set_yscale('log')
axis[0, 1].title.set_text("Weight vs KL-Divergence by Grade")

a = axis[1, 0].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 2], label='1')
b = axis[1, 0].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 2], label='2')
c = axis[1, 0].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 2], label='3')
d = axis[1, 0].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 2], label='4')
e = axis[1, 0].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 2], label='5')
#axis[1,0].legend((a,b,c,d,e), ('1','2','3','4','5'))
axis[1,0].set_xlabel("Weight")
axis[1,0].set_ylabel("Avg LL")
axis[1,0].title.set_text("Weight vs Avg LL by Grade")
axis[1,0].axhline(y=-49.48419132188499, linestyle='--', label='Normal LNB')
axis[1,0].axhline(y=-108.27602065828165, linestyle='-', label='True avg LL')
axis[1,0].legend(loc="best")

a = axis[1, 1].scatter(rows[rows[:, 1] == 1, 0], rows[rows[:, 1] == 1, 4], label='1')
b = axis[1, 1].scatter(rows[rows[:, 1] == 2, 0], rows[rows[:, 1] == 2, 4], label='2')
c = axis[1, 1].scatter(rows[rows[:, 1] == 3, 0], rows[rows[:, 1] == 3, 4], label='3')
d = axis[1, 1].scatter(rows[rows[:, 1] == 4, 0], rows[rows[:, 1] == 4, 4], label='4')
e = axis[1, 1].scatter(rows[rows[:, 1] == 5, 0], rows[rows[:, 1] == 5, 4], label='5')
#axis[1,1].legend((a,b,c,d,e), ('1','2','3','4','5'))
axis[1,1].set_xlabel("Weight")
axis[1,1].set_ylabel("KL-Divergence")
axis[1,1].title.set_text("Weight vs KL-Divergence by Grade")
axis[1,1].set_yscale('log')
axis[1,1].axhline(y=0.05069808838177512, linestyle='--', label='Normal LNB')
axis[1,1].legend(loc="best")

plt.show()

# 3D PLOT FOR AVG LL
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 2])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('Average LL')
axis.title.set_text('Synthetic dataset - Classified by varying grade and varying weight - Forced to use entire budget\nAVERAGE LOG LIKELIHOOD\nLNB = -49.48419132188499, TRUE = -108.27602065828165')
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
axis.title.set_text('Synthetic dataset - Classified by varying grade and varying weight - Forced to use entire budget\nAVERAGE LOG LIKELIHOOD')
plt.show()

# 3D PLOT FOR KL-DIVERGENCE
figure = plt.figure()
axis = figure.add_subplot(projection='3d')
axis.scatter(rows[::, 0], rows[::, 1], rows[::, 4])
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.set_zlabel('KL-Divergence')
axis.title.set_text('Synthetic dataset - Classified by varying grade and varying weight - Forced to use entire budget\nKL-DIVERGENCE\nLNB = 0.05069808838177512')
plt.show()

# CONTOUR PLOT FOR KL-DIVERGENCE
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
        Z[i, j] = temp[j, 4]
CS = axis.contour(weights, grades, Z)
axis.clabel(CS, inline=True, fontsize=10)
axis.set_xlabel('Weight')
axis.set_ylabel('Grade')
axis.title.set_text('Synthetic dataset - Classified by varying grade and varying weight - Forced to use entire budget\nKL-DIVERGENCE')
plt.show()