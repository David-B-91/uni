import pandas as pd
import numpy as np
import random
import time

from math import sqrt
from sklearn import preprocessing
from sklearn.metrics import mean_squared_error, r2_score, mean_absolute_error
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression, Ridge, SGDRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn.tree import DecisionTreeRegressor
from sklearn.ensemble import RandomForestRegressor, GradientBoostingRegressor
from sklearn.svm import SVR, LinearSVR
from sklearn.neural_network import MLPRegressor

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


def load_data():
    return pd.read_csv("diamonds.csv")


def init_analysis():
    # look at the head and tail of the data set
    print(data.head(n=2))
    print(data.tail(n=2))
    # show the types of each of the data inputs
    print(data.dtypes)
    # check if there are any null values
    print("Missing values:", data.isnull().values.any())
    print('\n')


# convert cate
def preprocess():
    """
    print("cut: ", data['cut'].unique())
    print("color: ", data['color'].unique())
    print("clarity: ", data['clarity'].unique())
    """
    # create label encoder object to be used to convert categories
    # may use a different encoder, depending on later results
    le = preprocessing.LabelEncoder()
    # convert categories into numeric
    data['cut'] = le.fit_transform(data['cut'])
    data['color'] = le.fit_transform(data['color'])
    data['clarity'] = le.fit_transform(data['clarity'])
    # remove index column
    data.drop(data.columns[0], axis=1, inplace=True)
    """
    print("cut: ", data['cut'].unique())
    print("color: ", data['color'].unique())
    print("clarity: ", data['clarity'].unique())
    """


def split():
    # remove price and the indexing columns from the dataset,
    # price removed because this is the value we are looking to predict
    # indexing column removed because it effects the data analysis
    cols = [col for col in data.columns if col not in ['price']]
    trim_data = data[cols]

    # scaling seems to have little impact on the results of regression
    """
    # can change the type of scaler here
    scaler = preprocessing.RobustScaler()
    scaled_data = scaler.fit_transform(trim_data)
    scaled_data = pd.DataFrame(scaled_data,
                               columns=['carat', 'cut', 'color', 'clarity', 'depth', 'table', 'x', 'y', 'z'])
    fig, (ax1, ax2) = plt.subplots(ncols=2, figsize=(6, 5))
    ax1.set_title('Before Scaling')
    sns.kdeplot(data['carat'], ax=ax1)
    sns.kdeplot(data['cut'], ax=ax1)
    sns.kdeplot(data['color'], ax=ax1)
    sns.kdeplot(data['clarity'], ax=ax1)
    sns.kdeplot(data['depth'], ax=ax1)
    sns.kdeplot(data['table'], ax=ax1)
    sns.kdeplot(data['x'], ax=ax1)
    sns.kdeplot(data['y'], ax=ax1)
    sns.kdeplot(data['z'], ax=ax1)
    ax2.set_title('After Min-Max Scaling')
    sns.kdeplot(scaled_data['carat'], ax=ax2)
    sns.kdeplot(scaled_data['cut'], ax=ax2)
    sns.kdeplot(scaled_data['color'], ax=ax2)
    sns.kdeplot(scaled_data['clarity'], ax=ax2)
    sns.kdeplot(scaled_data['depth'], ax=ax2)
    sns.kdeplot(scaled_data['table'], ax=ax2)
    sns.kdeplot(scaled_data['x'], ax=ax2)
    sns.kdeplot(scaled_data['y'], ax=ax2)
    sns.kdeplot(scaled_data['z'], ax=ax2)
    plt.show()
    print(trim_data.head())
    trim_data = scaled_data
    print(trim_data.head())
    """

    target = data['price']
    data_train, data_test, target_train, target_test = train_test_split(trim_data, target,
                                                                        test_size=train_test_split_test_size,
                                                                        random_state=seed)
    """
    print(data_train.head())
    print(data_test.head())
    print(target_train.head())
    print(target_test.head())
    """
    return data_train, data_test, target_train, target_test


def build_model():
    models = []
    # Linear Regression
    lr = LinearRegression()
    models.append(lr)
    # K-Nearest Neighbour
    knn = KNeighborsRegressor()
    models.append(knn)
    # Ridge Regression
    ridge = Ridge()
    models.append(ridge)
    # Decision Tree Regress
    dt = DecisionTreeRegressor()
    models.append(dt)
    # Random Forest Regression
    rf = RandomForestRegressor(n_estimators=100)
    models.append(rf)
    # gradient Boosting regression
    gb = GradientBoostingRegressor()
    models.append(gb)
    # SGD Regression
    sgd = SGDRegressor()
    models.append(sgd)
    # support vector regression
    svr = SVR(gamma='scale')
    models.append(svr)
    # linear SVR
    lsvr = LinearSVR()
    models.append(lsvr)
    # multi-layer perceptron regression
    mlp = MLPRegressor(early_stopping=True, learning_rate_init=0.5)
    models.append(mlp)

    for model in models:
        evaluate(model)


def evaluate(model):

    print(model)
    start_time = time.time()
    pred = model.fit(data_train, target_train).predict(data_test)
    end_time = time.time()
    etime = end_time - start_time

    mse = mean_squared_error(target_test, pred)
    print("MSE: %.2f" % mse)
    print("RMSE: %.2f" % sqrt(mse))
    print("R2: %.2f" % r2_score(target_test, pred))
    print("MAE: %.2f" % mean_absolute_error(target_test, pred))
    print("Execution Time: %.2f" % etime)
    print('\n')


if __name__ == '__main__':
    # Step 1: Load Data
    data = load_data()
    # Step 2: Initial Data Analysis
    init_analysis()
    # Step 3: Preprocess Data
    # Step 4: Exploratory Data Analysis
    preprocess()
    data_train, data_test, target_train, target_test = split()
    # Step 5: Build Regression Models Using Training Data
    build_model()
    # Step 6: Evaluate modesl by using cross validation (Optional)
    # Step 7: Assess model on the test data
