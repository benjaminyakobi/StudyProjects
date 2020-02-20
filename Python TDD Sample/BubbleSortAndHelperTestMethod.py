import unittest
from TddBmiCalc_And_BubbleSort import TddBmiCalc_And_BubbleSort

class TddBmiCalcTest_And_BubbleSortTest(unittest.TestCase):
    def test_BubbleSort(self):
        # stub
        stub1 = [5,3,4,1,2]
        stub2 = [1,2,3,4,5,6]
        stub3 = []
        stub4 = None

        # assume
        expected1 = [1,2,3,4,5]
        expected2 = [1,2,3,4,5,6]
        expected3 = []
        expected4 = False

        # action
        result1 = TddBmiCalc_And_BubbleSort.BubbleSort(stub1)
        result2 = TddBmiCalc_And_BubbleSort.BubbleSort(stub2)
        result3 = TddBmiCalc_And_BubbleSort.BubbleSort(stub3)
        result4 = TddBmiCalc_And_BubbleSort.BubbleSort(stub4)


        # expect/assert
        self.assertEqual(result1, expected1)
        self.assertEqual(result2, expected2)
        self.assertEqual(result3, expected3)
        self.assertEqual(result4, expected4)


    def test_CheckIfEverythingInTheArray(self):
        # stub
        stub1 = [10,30,20,50,1,2,3,4]
        stub2 = [1,2,0,5,3]

        # assume
        expected1 = True
        expected2 = True

        # action
        result1 = TddBmiCalc_And_BubbleSort.CheckIfEverythingInTheArray(stub1)
        result2 = TddBmiCalc_And_BubbleSort.CheckIfEverythingInTheArray(stub2)

        # expect/assert
        self.assertEqual(result1, expected1)
        self.assertEqual(result2, expected2)

if __name__ == '__main__':
    unittest.main()