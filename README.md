# A implementaion of LDFI for microservice system

## Done

### Parse
Parse the TraceSegment of skyalking to spans.

### Build Graph
Build DAG from the spans of trace.

### Boolean Encoder
convert the graph paths to boolean expression.

### SAT solover
convert the boolean expression to CNF and solve the problem wtih sat4j lib.
