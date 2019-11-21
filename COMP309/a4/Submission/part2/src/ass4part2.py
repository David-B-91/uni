import pandas as pd
import numpy as np
import random
import time

from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.neural_network import MLPClassifier
from sklearn.linear_model import LogisticRegression


import matplotlib.pyplot as plt
# import seaborn as sns

# using seed 309 from assignment brief.
seed = 309

# Freeze the random seed
random.seed(seed)
np.random.seed(seed)

# split test/training 30/70
train_test_split_test_size = 0.3

# Training settings
alpha = 0.1  # step size
max_iters = 50  # max iterations


def build_model():
    models = []
    # kNN
    knn = KNeighborsClassifier()
    models.append(knn)
    # Naive Bayes
    nb = GaussianNB()
    models.append(nb)
    # SVM
    svm = SVC(gamma='scale')
    models.append(svm)
    # Decision Tree
    dt = DecisionTreeClassifier()
    models.append(dt)
    # Random Forest
    rf = RandomForestClassifier(n_estimators=100)
    models.append(rf)
    # AdaBoost
    ada = AdaBoostClassifier()
    models.append(ada)
    # Gradient Boosting
    gb = GradientBoostingClassifier()
    models.append(gb)
    # Linear Discriminant Analysis
    lda = LinearDiscriminantAnalysis()
    models.append(lda)
    # Multi-Layer Perceptron
    mlp = MLPClassifier(early_stopping=True, learning_rate_init=0.01)
    models.append(mlp)
    # Logistic Regression
    lr = LogisticRegression(solver='liblinear', max_iter=200)
    models.append(lr)

    for model in models:
        evaluate(model)


def evaluate(model):
    print(model)
    start_time = time.time()
    pred = model.fit(training_set, training_target).predict(test_set)
    end_time = time.time()
    etime = end_time - start_time
    print("Accuracy: %.2f" % accuracy_score(test_target, pred))
    print("Precision: %.2f" % precision_score(test_target, pred))
    print("Recall: %.2f" % recall_score(test_target,pred))
    print("F1-Score: %.2f" % f1_score(test_target, pred))
    print("AUC: %.2f" % roc_auc_score(test_target, pred))
    print("Execution time: %.2f" % etime)
    print('\n')


def load_data():
    # na_values = " ?" whitespace included, as it is being read from the file including the whitespace
    # reading only removes the ',' that separate the values
    # header = None, because the files have no column headers
    # skiprows=[0] in loading adult.test because adult.test starts with a row having only "|1x3 Cross validator"
    return pd.read_csv("adult.data", na_values=" ?", header=None),\
           pd.read_csv("adult.test", skiprows=[0], na_values=" ?", header=None)


def init_analysis():
    print(data_train.head())
    print(data_test.head())
    print(data_train.dtypes)
    print(data_test.dtypes)
    print("Train Missing values:", data_train.isnull().values.any())
    print("Test Missing values:", data_test.isnull().values.any())


def preprocess():
    # deal with missing values
    fill_missing_values(data_train)
    fill_missing_values(data_test)
    print("Train Missing values:", data_train.isnull().values.any())
    print("Test Missing values:", data_test.isnull().values.any())
    # convert categorical values into numeric
    convert_to_numeric(data_train)
    convert_to_numeric(data_test)
    # add headers after converting, because errors
    add_header(data_train)
    add_header(data_test)
    # split the data, remove 'salary' from both sets, keep the training 'salary' values separately
    target = data_train['salary']
    training_set = data_train.drop(['salary'], axis=1)
    test_target = data_test['salary']
    test_set = data_test.drop(['salary'], axis=1)
    return training_set, test_set, target, test_target
    # print(data_train.head())
    # print(data_train.dtypes)
    # print(data_test.dtypes)


def fill_missing_values(d):
    # drop any rows (axis=0) that have more than half (thresh=d.shape[1]/2) entries missing,
    # and update the frame (inplace=true)
    d.dropna(axis=0, thresh=(d.shape[1]/2), inplace=True)
    # impute any remaining missing values, using most frequent.
    # https://stackoverflow.com/questions/32617811/imputation-of-missing-values-for-categories-in-pandas
    d.fillna(d.mode().iloc[0], inplace=True)


def convert_to_numeric(d):
    # convert columns 1, 3, 5-9, 13-14, from categorical to numeric
    le = LabelEncoder()
    for i in range(d.shape[1]):
        if i in [1, 3, 5, 6, 7, 8, 9, 13, 14]:
            d[i] = le.fit_transform(d[i])


def add_header(d):
    # add column headers for easier reference during preprocessing and data exploration.
    d.columns = ["age", "workclass", "final_weight", "education", "highest_education", "marital_status",
                 "occupation", "relationship", "race", "sex", "capital_gain", "capital_loss",
                 "hours_per_week", "native_country", "salary"]


if __name__ == '__main__':
    data_train, data_test = load_data()
    init_analysis()
    training_set, test_set, training_target, test_target = preprocess()
    build_model()