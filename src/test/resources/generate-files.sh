mkdir -p test-files

for i in {1..3}; do
    echo "# Test $i" > test-files/file$i.md
    echo "Test file $i" > test-files/file$i.txt
done