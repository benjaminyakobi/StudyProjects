print ("===========------------------===========\n"
           "Homework 3 by Benyamin Yakobi, 323492835\n"
       "===========------------------===========")
import json
import copy
class Collection(object):

    def __init__(self, iterable=None):
        if iterable is None:
            self.iterable = ()
        else:
            self.iterable = tuple(iterable)

    def first(self):
        ''':return: first element in list's object'''
        return self.iterable[0]

    def last(self):
        ''':return:  last element in list's object'''
        return self.iterable[-1]

    def take(self, amount):
        ''':param amount: we want all the element in range 0 to amount
                req_result: Collection with the required result depend in 'amount' value
        :return: we return all the elements in the collection till we reach index 'amount'.
                        if amount > length of collection we return the whole collection.'''
        return Collection(copy.deepcopy(self.iterable[:amount]))

    def append(self, *elements):
        ''':param element: is a tuple (because of *operator) that the function recieves from the user.
        :return: Collection type (appended_iterable variable that will be Collection type at the end).
        in the function we convert str / other (except dict) iterators into a list.
        after the manipulations we send the list that we get to the constructor to get back
        a Collection type which we return.'''
        appended_iterable, temp_iterable = tuple(self.iterable), ()
        for item in elements:
            temp_iterable = temp_iterable + (item, )
        x = appended_iterable + temp_iterable
        return Collection(x)

    def prepend(self, *element):
        '''SAME AS APPEND but instead adds element to the beggining of new collection
        :param element: is a tuple (because of *operator) that the function recieves from the user.
        :return: Collection type (appended_iterable variable that will be Collection type at the end).
        in the function we convert str / other (except dict) iterators into a list.
        after the manipulations we send the list that we get to the constructor to get back
        a Collection type which we return.'''
        prepended_iterable, temp_iterable = self.iterable, ()
        for item in element:
            temp_iterable = temp_iterable + (item, )
        return Collection(temp_iterable + prepended_iterable)

    def filter(self, *callbacks):
        ''':param callbacks: we put it into callbacksList before running the function mechanism
                self: self is actually our iterable so we assign self.iterable into a list (also for the function mechanism)
        :return: returning a Collection named: IterableList after using built-in map function on it'''
        iterableList = list(self.iterable)
        for func in callbacks:
            iterableList = list(filter(func, iterableList))
        return Collection(iterableList)

    def map(self, *callbacks):
        ''':param callbacks: we put it into callbacksList before running the function mechanism
                self: self is actually our iterable so we assign self.iterable into a list (also for the function mechanism)
        :return: returning a Collection named: IterableList after using built-in map function on it'''
        iterableList = self.iterable
        for func in callbacks:
            iterableList = map(func, iterableList)
        return Collection(iterableList)

    def reduce(self, callback, initial=0):
        ''':param callback: is function that the function receives
        :param initial: is the initial sum, we add this number to sum of our list
        :return: sum of iterable + initial value'''
        sum = initial
        for item in self.iterable:
            sum = callback(sum, item)
        return sum

    def sort(self, key=None, reversed=False):
        ''':param key: key of sort that the user want
        :param reversed: boolian value, if True we get backwards sort
        :return: new Sorted Collection'''
        tempList = list(self.iterable)
        if key is None:
            return Collection(sorted(tempList, key=key, reverse=reversed))
        else:
            return Collection(sorted(tempList, key=lambda item: item[key], reverse=reversed))

    def set(self, position, value):
        ''':param position: integer value -> place in the list that we want insert a value
        :param value: value that we want to add to our Collection
        :return: Copy of the original Collection with or without the value (depend on position)
        if position bigger than length of the collection and smaller than we just return
        a Copy of the original Collection.'''
        newOrCopiedIterable = list(self.iterable)
        if position < 0 or position > len(self.iterable):
            return Collection(newOrCopiedIterable)
        else:
            newOrCopiedIterable.insert(position, value)
            return Collection(newOrCopiedIterable)

    def pluck(self, key):
        ''':param key: Items of Collection should be dictionary before we use the key value
        :return: if Collection Items are Dictionary, than we return Collection of all values that
        are saved under the given key of the dictionary.
        If key doesn't exist in the dictionary, we return the original Collection.'''
        newIterableByKey = []
        for item in self.iterable:
            if type(item) is not dict or key not in item:
                return Collection(self.iterable)
            else:
                newIterableByKey.append(item[key])
        return Collection(newIterableByKey)

    def values(self):
        ''':return: copy of the internal values of the original collection'''
        return Collection(self.iterable)

    def unique(self):
        ''':return: new collection of the unique internal values of the original collection '''
        #return Collection(dict.fromkeys(self.iterable))
        tempList = []
        [tempList.append(copy.deepcopy(item)) for item in self.iterable if item not in tempList]
        return Collection(tempList)

    def tap(self, callback):
        ''':return: passing by value each element in the original collection to a given function that stored in callback'''
        [callback(item) for item in self.iterable]

    def __getitem__(self, position):
        ''':param: position holds the required position the user want iformation from
        :return: if position value is bigger than len of Collection than None,
        Otherwise we return the value that is stored in the Collection[position]'''
        if position > len(self.iterable):
            return None
        return self.iterable.__getitem__(position)

    def __add__(self, other):
        ''':param: other which can be a Collection or another kind of iterable or type
        :return: each time that the function is called we:
        1. convert everything into string and add the two lists
        2. we return a new Collection by calling the class Constructor'''
        newOther, newIterable = list(other), list(self.iterable)
        return Collection(newIterable+newOther)

    def __sub__(self, other):
        ''':param: other which can be a Collection or another kind of iterable or type
        :return: each time that the function is called we:
        1. convert everything into string and subtract the two lists by using set() function
        2. we return a new Collection by calling the class Constructor'''
        newOther, newIterable = list(other), list(self.iterable)
        return Collection(set(newIterable)-set(newOther))

    def __len__(self):
        ''':return: length of the Collection (which passed by self)'''
        return len(self.iterable)

    def __contains__(self, element):
        ''':return: True if exists in the Collection, else: False'''
        return True if element in self.iterable else False

    def __eq__(self, other):
        ''':return: using set() to compare between two Collections
        if they are equal than return True, else False'''
        return True if set(self.iterable) == set(other) else False

    def __ne__(self, other):
        ''':return: using set() to compare between two Collections
        if they are equal than return False, else True'''
        return True if set(self.iterable) != set(other) else False

    def __str__(self):
        ''':return: string representation of the instance
        The goal of str() is to be readable'''
        return str(self.iterable)

    def __repr__(self):
        ''':return: string representation of the instance + Class name which is Collection.
        The goal of repr() is to be readable & unambiguous'''
        return 'Collection' + repr(self.iterable)

def enumerate_waze(filename):
    ''':param filename: contains name of a file that we want to open
    :return: Collection of data that was stored in the file that we open.'''
    with open(filename, 'r') as f:
        data = json.load(f)
    return Collection(data)

def clean_noise(dataFromFile):
    ''':param: dataFromFile is the data that came from file that we open with enumerate_waze
    :return: cleanedDataFromFile which is data without irrelevant data'''
    noiseList, cleanedDataFromFile = {'country', 'reliability', 'user'}, Collection()
    for item in dataFromFile:
        if any(noise not in item for noise in noiseList):
            pass
        else:
            cleanedDataFromFile = cleanedDataFromFile.append(item)
    return Collection(cleanedDataFromFile)

def complete_values(dataFromFile):
    ''':param dataFromFile: json file data
    :return: completed value of alerts that  do not have a type value
    should be given a type='other' by default.'''
    [item.update(type='other') for item in dataFromFile if 'type' not in item]
    return Collection(dataFromFile)

def get_average_reliability(dataFromFile):
    ''':param dataFromFile: json file data
    :return: average reliability for all alerts in Israel.
            if length of sumList is 0 than return 0 to avoid devide by Zero error'''
    sumList = dataFromFile.filter(lambda key: key['country'] == 'IL').pluck(key='reliability')
    if len(sumList) > 0:
        return sum(sumList) / len(sumList)
    else:
        return 0

def get_top_100_users(dataFromFile):
    ''':param dataFromFile: json file data
    :return: collection of the top 100 most active users based on the amount of alerts
    they posted sorted from most popular to least.
    In this function we use filter, append, sort, unique & values functions from the class we implemented before'''
    tempList, tempDict = [], {}
    [tempList.append(item) for item in dataFromFile.pluck(key='user')]
    tempDict, tempList = Collection([{'user': item, 'counter': tempList.count(item)} for item in tempList]).sort(key='counter', reversed=True).unique().take(100), []
    [tempList.append(item['user']) for item in tempDict]
    return Collection(tempList)

def get_top_accident_notifyer(dataFromFile):
    ''':param dataFromFile: json file data
    :return: the user who posts the most amount of accidents.
    In this function we use filter, append, sort, unique & values functions from the class we implemented before'''
    tempList, tempDict = [], {}
    topAccidentUsers = dataFromFile.filter(lambda k: k['type'] == 'accident')
    [tempList.append(item) for item in topAccidentUsers.pluck(key='user')]
    tempDict, tempList = Collection([{'user': item, 'counter': tempList.count(item)} for item in tempList]).sort(key='counter', reversed=True).unique().take(100), []
    [tempList.append(item['user']) for item in tempDict]
    return tempList[0]

def get_alert_types_by_country(dataFromFile):
    ''':param dataFromFile: json file data
    :return: collection of alert types and their counts by Country.
    In this function we use unique & values class-functions to remove duplicates
    and get the relevant data. we also use append, map & filter functions'''
    tempCollection, finalCollection = Collection(), Collection()
    for countryKey in dataFromFile.pluck(key='country').unique().values():
        for typeKey in dataFromFile.filter(lambda k: k['country'] == countryKey).pluck('type').unique().values():
            dataValues = {typeKey: len(dataFromFile.filter(lambda k: k['country'] == countryKey and k['type'] == typeKey))}
            tempCollection = tempCollection.append(dataValues)
        tempDict = {'country': countryKey, 'data': (Collection(tempCollection))}
        finalCollection, tempCollection = finalCollection.append(tempDict), Collection()
    return Collection(finalCollection)

filename = 'waze.json'

'''
###################
## Print Section ##
###################

print(enumerate_waze(filename))
# => Collection({"country": "US", "reliability": 6, "user": "lsteffans0"......)
print("========dataFromFile============")
c = enumerate_waze(filename)
c.tap(print)
print("=========clean_noise===========")
c = clean_noise(c)
c.tap(print)
print("=========complete_values===========")
c = complete_values(c)
c.tap(print)
print("========isreael_reliability============")
israeli_reliability = get_average_reliability(c)
print(israeli_reliability)
print("=========top_100_users===========")
top_users = get_top_100_users(c)
print(top_users)
print("=========top_accidents===========")
top_accidents = get_top_accident_notifyer(c)
print(top_accidents)
# => 'lsteffans0'
print("=========alerts_types===========")
alerts_types = get_alert_types_by_country(c)
alerts_types.tap(print)
print("=========all other things===========")

c = Collection()
print(c)
c1 = Collection([1,2,3,4])
c2 = Collection([1,2])
print(repr(c1))
# => Collection(1,2,3,4)
print(c2)
# => (1,2)
c1 = Collection([1,2,3,4])
c2 = Collection([1,2])
print(c1)
# => (1,2,3,4)
print(c2)
# => (1,2)
c1 = Collection([1, 2, 3, 4])
c2 = Collection([1, 2])
c3 = Collection([1, 2, 3, 4])
print(c1 == c2)
# => False
print(c1 == c3)
# => True
c1 = Collection([1,2,3,4])
c2 = Collection([1,2])
print(1 in c1)
# => True
print(3 in c2)
# => False
print(len(c1))
# => 4
print(len(c2))
# => 2
c1 = Collection([1,2,3,4])
c2 = Collection([1,2])
iterable = 'hello'
print(c1 - c2)
# => Collection(3,4)
print(c1 - iterable)
# => Collection(1,2,3,4)
c1 = Collection([1,2,3,4])
c2 = Collection([1,2,3,4])
iterable = 'hello'
print(c1 + c2 + iterable)
# => Collection(1,2,3,4,1,2,3,4,'h','e','l','l','o')
c = Collection('hello')
print(c[0])
# => 'h'
print(c[-1])
# => 'o'
print(c[17])
# => None
c = Collection('HELLO')
c.tap(print)
# => 'H'
# => 'E'
# => 'L'
# => 'L'
# => 'O'
c = Collection('HELLO')
print(c.unique())
# => Collection('H','E','L','O')
c = Collection('HELLO')
print(c.values())
# => ('H', 'E', 'L', 'L', 'O')
c = Collection([{'name': 'Joe', 'age': 20},{'name': 'Jane', 'age': 13}])
print(c.pluck('age'))
# => Collection(20,13)
print(c.pluck('name'))
# => Collection('Joe','Jane')
c = Collection('HELLO')
print(c.set(0, 'h'))
# => Collection('h','E','L','L','O')
c = Collection('HELLO')
print(c.sort())
# => Collection('E','H','L','L','O')
print(c.sort(reversed = True))
# => Collection('O','L','L','H','E')
c = Collection([{'name': 'Joe', 'age': 20}, {'name': 'Jane', 'age': 13}])
print(c.sort(key='age'))
# => Collection({'name': 'Jane', 'age': 13},{'name': 'Joe', 'age':20})
c = Collection([1,2,3,4])
print(c.take(2))
# => Collection(1,2)
print(c.take(5))
# => Collection(1,2,3,4)
c = Collection([1,2,3,4])
print(c.append('hello'))
# => Collection(1,2,3,4,'hello')
print(c.append(1,2,3,4))
# => Collection(1,2,3,4,1,2,3,4)
print(c.append([1,2,3,4]))
# => Collection(1,2,3,4,[1,2,3,4])
c = Collection([1, 2, 3, 4, 5, 6, 7, 8])
print(c.filter(lambda number: number % 2, lambda number: number % 3))
c = Collection('hello')
print(c.map(str.upper, ord, chr))
c = Collection([1,2,3,4])
print(c.reduce(lambda x,y: x+y, 5))
# => 15
print(c.reduce(lambda x,y: x+y))
'''