from opyenxes.data_in.XUniversalParser import XUniversalParser
import os
import pprint
from graphviz import Digraph

print(os.listdir('.'))
path = 'repairExample.xes.gz'


def extract_activity(log):
    workflow_log = []
    for trace in log:
        workflow_trace = []
        for event in trace[0::2]:
            # get the event name from the event in the log
            event_name = event.get_attributes()['Activity'].get_value()
            workflow_trace.append(event_name)
        workflow_log.append(workflow_trace)
    return workflow_log


def create_succesors_event_map():
    w_net = dict()
    for w_trace in workflow_log:
        for i in range(0, len(w_trace) - 1):
            ev_i, ev_j = w_trace[i], w_trace[i + 1]
            if ev_i not in w_net.keys():
                w_net[ev_i] = set()
            w_net[ev_i].add(ev_j)
    return w_net


def create_graph_of_events(w_net, ev_counter):
    color_min = min(ev_counter.values())
    color_max = max(ev_counter.values())
    g = Digraph('G')
    g.attr(rankdir='LR')
    g.attr(shape='Mrecord')
    for event_in_net in w_net:
        value = ev_counter[event_in_net]
        color = int(float(color_max-value)/float(color_max-color_min)*100.00)
        my_color = "#ff9933" + str(hex(color))[2:]
        text = event_in_net + ' (' + str(value) + ")"
        g.node(event_in_net, label=text, style="rounded,filled", fillcolor=my_color)
        for preceding in w_net[event_in_net]:
            g.edge(event_in_net, preceding)
    return g


def count_event_occurences(workflow_log):
    ev_counter = dict()
    for w_trace in workflow_log:
        for ev in w_trace:
            ev_counter[ev] = ev_counter.get(ev, 0) + 1
    return ev_counter


with open(path) as log_file:
    # parse the log
    log = XUniversalParser().parse(log_file)[0]
    event = log[0][0]
    pp = pprint.PrettyPrinter(indent=4)
    pp.pprint(event.get_attributes())

    workflow_log = extract_activity(log)
    ev_counter = count_event_occurences(workflow_log)

    w_net = create_succesors_event_map()
    pp.pprint(w_net)

    g = create_graph_of_events(w_net, ev_counter)


    print(g.source)  # g.view() does not work, we have to print it online http://www.webgraphviz.com/
